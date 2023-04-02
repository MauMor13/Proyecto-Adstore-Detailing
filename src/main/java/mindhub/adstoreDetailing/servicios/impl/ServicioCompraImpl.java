package mindhub.adstoreDetailing.servicios.impl;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraProducto;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraServicio;
import mindhub.adstoreDetailing.models.*;
import mindhub.adstoreDetailing.repositorios.*;
import mindhub.adstoreDetailing.servicios.ServicioCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ServicioCompraImpl implements ServicioCompra {
    @Autowired
    RepositorioCompra repositorioCompra;
    @Autowired
    RepositorioProducto repositorioProducto;
    @Autowired
    RepositorioServicio repositorioServicio;
    @Autowired
    RepositorioCompraProducto repositorioCompraProducto;
    @Autowired
    RepositorioCompraServicio repositorioCompraServicio;
    @Autowired
    RepositorioTurnoServicio repositorioTurnoServicio;

    @Override
    public void guardar(Compra compra){
        repositorioCompra.save(compra);
    }
    @Override
    public void agregarProductos(ArrayList<RealizarCompraProducto> productos, Compra compra) {
        for (RealizarCompraProducto producto : productos) {

            Producto productoObj = this.repositorioProducto.findById(producto.getId()).orElseThrow();
            CompraProducto compraProducto = new CompraProducto(compra, productoObj, producto.getCantidad());

            compra.sumarCompraProducto(compraProducto);

            repositorioCompraProducto.save(compraProducto);
        }
    }
    @Override
    public void agregarServicios(ArrayList<RealizarCompraServicio> servicios, Compra compra, LocalDateTime fechaDelServicio) {
        TurnoServicio nuevoTurnoServicio = new TurnoServicio(fechaDelServicio);
        repositorioTurnoServicio.save(nuevoTurnoServicio);
        LocalDateTime tiempoDuracion = fechaDelServicio;
        for (RealizarCompraServicio servicio : servicios) {
            Servicio servicioObj = this.repositorioServicio.findById(servicio.getId()).orElseThrow();
            tiempoDuracion = tiempoDuracion.plus(servicioObj.getDuracion());
            CompraServicio compraServicio = new CompraServicio(compra,servicioObj);
            nuevoTurnoServicio.sumarCompraServicio(compraServicio);
            nuevoTurnoServicio.setNotificado(false);
            compra.sumarCompraServicio(compraServicio);
            repositorioCompraServicio.save(compraServicio);
        }
        nuevoTurnoServicio.setFechaHoraSalida(tiempoDuracion);
        repositorioTurnoServicio.save(nuevoTurnoServicio);
    }
    @Override
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
            return cuerpoRespuesta = "Pago no realizado";
        }
    }
//    @Override
//    public String conectarHomebanking(String URLobjetivo, String parametros) {
//        HttpURLConnection connection = null;
//        try {
//            // Crear conexión
//            URL url = new URL(URLobjetivo);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setRequestProperty("Content-Length", Integer.toString(parametros.getBytes().length));
//            connection.setUseCaches(false);
//            connection.setDoOutput(true);
//
//            // Envío de solicitud
//            DataOutputStream envio = new DataOutputStream(connection.getOutputStream());
//            envio.writeBytes(parametros);
//            envio.close();
//
//            // Obtener respuesta
//            int codigoRespuesta = connection.getResponseCode();
//            InputStream inputStream;
//            if (codigoRespuesta >= 200 && codigoRespuesta <= 299) {
//                inputStream = connection.getInputStream();
//            } else {
//                inputStream = connection.getErrorStream();
//            }
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
//            exception.printStackTrace();
//            return null;
//        } finally {
//            if (connection != null){{
//                connection.disconnect();}
//            }
//        }
//    }
}

