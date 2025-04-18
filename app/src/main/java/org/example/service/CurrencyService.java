package org.example.service;

import org.springframework.cache.annotation.Cacheable;
import org.example.model.CurrencyRate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPConnection;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPPart;
import org.example.model.RequestLog;
import org.example.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class CurrencyService {

    private static final String SERVER_URI = "http://web.cbr.ru/";
    private static final String SOAP_URL = "https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx";
    private static final String SOAP_ACTION = "http://web.cbr.ru/GetCursOnDateXML";

    @Autowired
    private RequestLogRepository requestLogRepository;

    @Cacheable(value = "currencyRates", key = "#date")
    public RequestLog getCurrencyRates(LocalDate date) {
        RequestLog result = new RequestLog();
        try {
            SOAPMessage soapMessage = createSoapRequest(date);
            String responseStr = sendSoapRequest(soapMessage);
            result.setRequestDate(date);
            result.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            result.setCurrencies(parseCurrencyRates(responseStr, result));
            requestLogRepository.save(result);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCurrencies(new ArrayList<>());
            requestLogRepository.save(result);
        }
        return result;
    }

    private SOAPMessage createSoapRequest(LocalDate date) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody soapBody = envelope.getBody();

        SOAPElement soapBodyElem = soapBody.addChildElement("GetCursOnDateXML", "", SERVER_URI);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("On_date", "", SERVER_URI);
        soapBodyElem1.addTextNode(date.toString());

        soapMessage.getMimeHeaders().addHeader("SOAPAction", SOAP_ACTION);
        soapMessage.saveChanges();

        return soapMessage;
    }

    private String sendSoapRequest(SOAPMessage soapMessage) throws Exception {
        try (SOAPConnection soapConnection = SOAPConnectionFactory.newInstance().createConnection();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            soapMessage.writeTo(outputStream);

            SOAPMessage soapResponse = soapConnection.call(soapMessage, SOAP_URL);
            try (ByteArrayOutputStream responseOutput = new ByteArrayOutputStream()) {
                soapResponse.writeTo(responseOutput);
                String responseStr = responseOutput.toString();
                return responseStr;
            }
        }
    }

    public List<CurrencyRate> parseCurrencyRates(String xmlResponse, RequestLog requestLog) {
        List<CurrencyRate> rates = new ArrayList<>();
        try {
            Document doc = parseXmlResponse(xmlResponse);
            NodeList valuteNodes = doc.getElementsByTagName("ValuteCursOnDate");

            for (int i = 0; i < valuteNodes.getLength(); i++) {
                Node node = valuteNodes.item(i);
                CurrencyRate currencyRate = extractCurrencyRate(node, requestLog);
                if (currencyRate != null) {
                    rates.add(currencyRate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rates;
    }

    private Document parseXmlResponse(String xmlResponse) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlResponse)));
    }

    private CurrencyRate extractCurrencyRate(Node node, RequestLog requestLog) {
        String name = "";
        String code = "";
        double rate = 0.0;
        int nominal = 1;

        NodeList childNodes = node.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node child = childNodes.item(j);

            switch (child.getNodeName()) {
                case "Vname":
                    name = child.getTextContent().trim();
                    break;
                case "VchCode":
                    code = child.getTextContent().trim();
                    break;
                case "Vcurs":
                    rate = Double.parseDouble(child.getTextContent().trim());
                    break;
                case "Vnom":
                    nominal = Integer.parseInt(child.getTextContent().trim());
                    break;
            }
        }

        if (!name.isEmpty() && !code.isEmpty()) {
            CurrencyRate currencyRate = new CurrencyRate();
            currencyRate.setName(name);
            currencyRate.setCharCode(code);
            currencyRate.setValueRate(String.valueOf(rate));
            currencyRate.setRequestLog(requestLog);
            currencyRate.setNominal(String.valueOf(nominal));
            return currencyRate;
        }
        return null;
    }
}