package mindhub.adstoreDetailing.servicios.impl;

import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraDTO;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraProducto;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraServicio;
import mindhub.adstoreDetailing.models.*;
import mindhub.adstoreDetailing.repositorios.RepositorioProducto;
import mindhub.adstoreDetailing.repositorios.RepositorioServicio;
import mindhub.adstoreDetailing.servicios.ServicioCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

@Service
public class ServicioCompraImpl implements ServicioCompra {
    @Autowired
    RepositorioProducto repositorioProducto;
    @Autowired
    RepositorioServicio repositorioServicio;
    @Autowired
    RealizarCompraServicio realizarCompraServicio;
    @Autowired
    RealizarCompraProducto realizarCompraProducto;
//    @Override
//    public void addCompraProducto(RealizarCompraProducto realizarCompraProducto, Compra compra) {
//        CompraProducto compraProducto = new CompraProducto();
//        compraProducto.setProducto(repositorioProducto.findById(realizarCompraProducto.getId()).orElseThrow(() -> new EntityNotFoundException("Producto not found")));
//        compraProducto.setCantidad(realizarCompraProducto.getCantidad());
//        compra.sumarCompraProducto(compraProducto);
//    }
//    @Override
//    public void addCompraServicio(RealizarCompraServicio realizarCompraServicio, Compra compra) {
//        CompraServicio compraServicio = new CompraServicio();
//        compraServicio.setServicio(repositorioServicio.findById(realizarCompraServicio.getId()).orElseThrow(() -> new EntityNotFoundException("Servicio not found")));
//        compraServicio.setFechaReserva(realizarCompraServicio.getFecha());
//        compra.sumarCompraServicio(compraServicio);
  //  }
    @Override
    public void agregarProductos(ArrayList<RealizarCompraProducto> productos, Compra compra) {
        for (RealizarCompraProducto producto : productos) {

            Producto productoObj = this.repositorioProducto.findById(producto.getId()).orElseThrow();
            CompraProducto compraProducto = new CompraProducto(compra, productoObj, producto.getCantidad());

            compra.sumarCompraProducto(compraProducto);
        }
    }
    @Override
    public void agregarServicios(ArrayList<RealizarCompraServicio> servicios, Compra compra) {
        for (RealizarCompraServicio servicio : servicios) {

            Servicio servicioObj = this.repositorioServicio.findById(servicio.getId()).orElseThrow();
            CompraServicio compraServicio = new CompraServicio(compra,servicioObj, servicio.getFecha());

            compra.sumarCompraServicio(compraServicio);
        }
    }

}
