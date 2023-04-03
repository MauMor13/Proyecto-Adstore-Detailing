package mindhub.adstoreDetailing.controladores;

import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/api")
public class ControladorPagoHomebanking {

//    public String pago(){
//        return conectarHomebanking("http://localhost:8090/api/pay", "number=1103-8874-3691-9681&cvv=787&amount=15000&description=lautaro");
//    }
    @Transactional
    @PostMapping("/pay")
    public String conectarHomebanking(@RequestParam String number, @RequestParam String cvv, @RequestParam Double amount, @RequestParam String description) {
        String cuerpoRespuesta;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<Object, Object> cuerpo = new LinkedMultiValueMap<>();
        cuerpo.add("number", number);
        cuerpo.add("cvv", cvv);
        cuerpo.add("amount", amount);
        cuerpo.add("description", description);
        String url = "http://localhost:8090/api/pay";
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity solicitud = new HttpEntity<>(cuerpo, httpHeaders);
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, solicitud, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.ACCEPTED) {
                return cuerpoRespuesta = responseEntity.getBody();
            } else {
                return cuerpoRespuesta = "Pago no realizado " + responseEntity.getStatusCode();
            }
        } catch (HttpClientErrorException.Forbidden e) {
            return cuerpoRespuesta = "BAD";
        }
    }

//    public static String conectarHomebanking(String URLobjetivo, String parametros)
    {
        HttpURLConnection connection = null;
//        try {

            //Crear conexión

//            URL url = new URL(URLobjetivo);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setRequestProperty("Content-Length", Integer.toString(parametros.getBytes().length));
//            connection.setUseCaches(false);
//            connection.setDoOutput(true);

            //Envio de solicitud

//            DataOutputStream envio = new DataOutputStream(connection.getOutputStream());
//            envio.writeBytes(parametros);
//            System.out.println(connection);
//            envio.close();

            //Obtener respuesta

//            InputStream inputStream = connection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuffer respuesta = new StringBuffer();
//            String linea;
//            while ((linea = bufferedReader.readLine()) != null){
//                respuesta.append(linea);
//                respuesta.append('\r');
//            }
//            bufferedReader.close();
//            return respuesta.toString();
//        } catch (Exception exception){
            // exception occurred, read response body from ErrorStream
//            InputStream errorStream = connection.getErrorStream();
//            if (errorStream != null) {
//                BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
//                StringBuffer errorResponse = new StringBuffer();
//                String errorLine;
//                try {
//                    while ((errorLine = errorReader.readLine()) != null){
//                        errorResponse.append(errorLine);
//                        errorResponse.append('\r');
//                    }
//                } catch (IOException e) {
//                    // handle the exception here
//                    e.printStackTrace();
//                } finally {
//                    // close the BufferedReader and handle any exception that may occur
//                    try {
//                        errorReader.close();
//                    } catch (IOException e) {
//                        // handle the exception here
//                        e.printStackTrace();
//                    }
//                }
//                System.err.println("Error response: " + errorResponse.toString());
//            }
//            exception.printStackTrace();
//            return null;
//        } finally {
//            if (connection != null){{
//                connection.disconnect();
//            }}
//        }
//    }
        }}
