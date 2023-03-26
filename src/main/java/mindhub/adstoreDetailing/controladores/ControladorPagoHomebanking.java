package mindhub.adstoreDetailing.controladores;

import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class ControladorPagoHomebanking {
    public static String conectarHomebanking(String URLobjetivo, String parametros){
        HttpURLConnection connection = null;
        try {

            //Crear conexión

            URL url = new URL(URLobjetivo);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(parametros.getBytes().length));
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Envio de solicitud

            DataOutputStream envio = new DataOutputStream(connection.getOutputStream());
            envio.writeBytes(parametros);
            envio.close();

            //Obtener respuesta

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer respuesta = new StringBuffer();
            String linea;
            while ((linea = bufferedReader.readLine()) != null){
                respuesta.append(linea);
                respuesta.append('\r');
            }
            bufferedReader.close();
            return respuesta.toString();
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        } finally {
            if (connection != null){{
                connection.disconnect();
            }}
        }
    }
}
