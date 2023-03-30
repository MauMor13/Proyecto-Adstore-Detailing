package mindhub.adstoreDetailing.servicios;

import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraProducto;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraServicio;
import mindhub.adstoreDetailing.models.Compra;

import java.util.ArrayList;

public interface ServicioCompra {
//   void addCompraProducto(RealizarCompraProducto realizarCompraProducto, Compra compra);
//   void addCompraServicio(RealizarCompraServicio realizarCompraServicio, Compra compra);
   void agregarProductos(ArrayList<RealizarCompraProducto> productos, Compra compra);
   void agregarServicios(ArrayList<RealizarCompraServicio> servicios, Compra compra);
}
