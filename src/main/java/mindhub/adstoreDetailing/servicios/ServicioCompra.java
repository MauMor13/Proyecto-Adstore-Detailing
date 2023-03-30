package mindhub.adstoreDetailing.servicios;
import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraProducto;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraServicio;
import java.util.ArrayList;
public interface ServicioCompra {
   void agregarProductos(ArrayList<RealizarCompraProducto> productos, Compra compra);
   void agregarServicios(ArrayList<RealizarCompraServicio> servicios, Compra compra);
   void guardar(Compra compra);
}
