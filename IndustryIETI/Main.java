import java.io.IOException;

public class Main {
	public static void main(String[] args) throws InterruptedException, IOException {
		baseDades BBDD = new baseDades();
		Servidor serv=new Servidor(4810);
		serv.inicial();
		
		
	}
}
