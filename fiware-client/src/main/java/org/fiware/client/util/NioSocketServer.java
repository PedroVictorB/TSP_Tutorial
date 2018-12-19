package org.fiware.client.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.fiware.client.UserInterface;
import org.fiware.client.entities.ContextResponsesSubscriptionContainer;

public class NioSocketServer {

    public NioSocketServer(UserInterface frame) {
        // Cria um canal que escuta na porta 8989
        AsynchronousServerSocketChannel listener;
        try {
            listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8989));
            // Espera um request
            listener.accept(null, new completeHandler(listener, frame));
        } catch (IOException ex) {
            Logger.getLogger(NioSocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class completeHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {
	
	AsynchronousServerSocketChannel listener;
	UserInterface frame;
	
	public completeHandler(AsynchronousServerSocketChannel listener, UserInterface frame) {
		this.listener = listener;
		this.frame = frame;
	}

	public void completed(AsynchronousSocketChannel ch, Void att) {
		// Aceita a conexão
        listener.accept(null, this);

        // Escreve a volta para o cliente (não necessário)
        //ch.write(ByteBuffer.wrap("Conversando!!!\n".getBytes()));

        // Buffer para ler mensagem do cliente (4K)
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        try {
            int bytesRead = ch.read(byteBuffer).get(20, TimeUnit.SECONDS);

            boolean running = true;
            while (bytesRead != -1 && running) {
                // Checagem de mensagem nula
                if (byteBuffer.position() > 2) {
                    byteBuffer.flip();

                    byte[] lineBytes = new byte[bytesRead];
                    byteBuffer.get(lineBytes, 0, bytesRead);
                    String line = new String(lineBytes);
                    String body = ApacheRequestFactory.getHttpMessageBodyFromString(line);
                    
                    ObjectMapper mapper = new ObjectMapper();
                    TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
                    Map<String, Object> object = mapper.readValue(body, typeRef);
                    
                    ArrayList entities = (ArrayList) object.get("data");
                    LinkedHashMap entity = (LinkedHashMap) entities.get(0);
                    LinkedHashMap temperature = (LinkedHashMap) entity.get("distance");
                    Double value = (Double) temperature.get("value");

                    // Chama o serviço para mudar a UI
                    System.out.println("Received: "+body);
                    frame.lblNewLabel_1.setText("Distance: "+value.toString()+" cm");
                    
                    ch.write(ByteBuffer.wrap(line.getBytes()));

                    byteBuffer.clear();

                    bytesRead = ch.read(byteBuffer).get(5, TimeUnit.SECONDS);
                } else {
                    // Linha nula é o fim da mensagem
                    running = false;
                }
            }

        } catch (InterruptedException e) {
            //e.printStackTrace();
        	System.out.println("Connection interrupted");
        } catch (ExecutionException e) {
            //e.printStackTrace();
        	System.out.println("Execution interrupted");
        } catch (TimeoutException e) {
            // Timeout de 20seg
            //ch.write(ByteBuffer.wrap("Good Bye\n".getBytes()));
            System.out.println("Connection timed out, closing connection");
        } catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("End of conversation");
        // Fecha a conexão se necessário
        if (ch.isOpen()) {
            System.out.println("Channel is open");
            try {
                ch.close();
                System.out.println("Channel is closed");
            } catch (IOException ex) {
                Logger.getLogger(NioSocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
		
	}

	public void failed(Throwable exc, Void attachment) {
		// TODO Auto-generated method stub
		
	}
	
}