package planillaje.Vehicular.planillaje.vehicular.Excepciones;

/*
Excepción para parametros invalidos
 */
public class BadRequestException extends  RuntimeException{
    public  BadRequestException(String mensaje){
         super(mensaje);
    }
}
