import Utilities.Client;


import java.net.UnknownHostException;


public class Main {

    public static void main(String[] args) throws UnknownHostException {

       Client client = new Client("localhost", 7071);
       client.run();
    }
}

