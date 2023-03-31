package mindhub.adstoreDetailing.servicios.impl;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraProducto;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraServicio;
import mindhub.adstoreDetailing.models.*;
import mindhub.adstoreDetailing.repositorios.*;
import mindhub.adstoreDetailing.servicios.ServicioCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Duration tiempoDuracion = Duration.ofMinutes(0);
        for (RealizarCompraServicio servicio : servicios) {
            Servicio servicioObj = this.repositorioServicio.findById(servicio.getId()).orElseThrow();
            tiempoDuracion.plus(servicioObj.getDuracion());
            CompraServicio compraServicio = new CompraServicio(compra,servicioObj);
            nuevoTurnoServicio.sumarCompraServicio(compraServicio);
            nuevoTurnoServicio.setNotificado(false);
            compra.sumarCompraServicio(compraServicio);
            repositorioCompraServicio.save(compraServicio);
        }
        nuevoTurnoServicio.setFechaHoraSalida(fechaDelServicio.plus(tiempoDuracion));
        repositorioTurnoServicio.save(nuevoTurnoServicio);
    }
    @Override
    public String conectarHomebanking(String URLobjetivo, String parametros){
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
            System.out.println(connection);
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
