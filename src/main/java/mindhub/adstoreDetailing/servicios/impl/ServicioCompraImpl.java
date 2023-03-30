package mindhub.adstoreDetailing.servicios.impl;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraProducto;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraServicio;
import mindhub.adstoreDetailing.models.*;
import mindhub.adstoreDetailing.repositorios.*;
import mindhub.adstoreDetailing.servicios.ServicioCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void agregarServicios(ArrayList<RealizarCompraServicio> servicios, Compra compra) {
        for (RealizarCompraServicio servicio : servicios) {

            Servicio servicioObj = this.repositorioServicio.findById(servicio.getId()).orElseThrow();
            CompraServicio compraServicio = new CompraServicio(compra,servicioObj);

            compra.sumarCompraServicio(compraServicio);

            repositorioCompraServicio.save(compraServicio);
        }
    }

}
