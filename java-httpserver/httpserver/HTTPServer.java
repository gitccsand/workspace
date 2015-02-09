package httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * HTTPServer is a relatively simple class with one job, and one job only:
 * wait for incoming connections, and send the connections over to an
 * HTTPRequest and an HTTPResponse.
 *
 * An HTTPServer is not required to use the rest of the httpserver classes,
 * and might not be the best base server for one to use. It exists solely to
 * provide an existing mechanism for using the rest of the httpserver package.
 */
public class HTTPServer {

  public static final int defaultPort = 8000;

  /** The server's name */
  private static String serverName = "Simple Java Server";

  /** The server's version */
  private static String serverVersion = "0.0.1";

  /** Extra information about the server */
  private static String serverETC = "now in Glorious Extra Color";

  public int port;
  private ServerSocket socket = null;


  /**
   * Create an HTTPServer with default values.
   */
  public HTTPServer() {
    this(defaultPort);
  }

  /**
   * Create an HTTPServer specifying the server information.
   * @param name The name of the server.
   * @param version The version of the server.
   * @param etc More information about the server.
   */
  public HTTPServer(String name, String version, String etc) {
    this(defaultPort, name, version, etc);
  }

  /**
   * Create an HTTPServer specifying the server port and information
   * @param port The port the server will listen on.
   * @param name The name of the server.
   * @param version The version of the server.
   * @param etc More information about the server.
   */
  public HTTPServer(int port, String name, String version, String etc) {
    this(port);
    setServerInfo(name, version, etc);
  }

  /**
   * Create an HTTPServer specifying the port.
   * @param port The port the server will listen on.
   */
  public HTTPServer(int port) {
    setPort(port);
  }

  /**
   * Tell the server to run.<p>
   * 
   * Unless you specify the port with {@link HTTPServer#setSocket()},
   * the server will run on http://127.0.0.1:{@value #defaultPort}.
   */
  public void run() {
    try {
      socket = new ServerSocket();

      System.out.println("Starting HTTPServer at http://127.0.0.1:" + getPort());

      socket.setReuseAddress(true);
      socket.bind(new InetSocketAddress(getPort()));

      while (true) {
        Socket connection = null;
        try {
          connection = socket.accept();
          HTTPRequest request = new HTTPRequest(connection);
          Thread t = new Thread(request);
          t.start();
        }
        catch (SocketException e) {
          /*  This typically occurs when the client breaks the connection,
              and isn't an issue on the server side, which means we shouldn't
               break
           */
          System.err.println("Client broke connection early!");
          e.printStackTrace();
        }
        catch (IOException e) {
          /*  This typically means there's a problem in the HTTPRequest
           */
          System.err.println("IOException. Probably an HTTPRequest issue.");
          e.printStackTrace();
        }
        catch (HTTPException e) {
          System.err.println("HTTPException.");
          e.printStackTrace();
        }
        catch (Exception e) {
          /*  Some kind of unexpected exception occurred, something bad might
              have happened.
           */
          System.err.println("Generic Exception!");
          e.printStackTrace();

          /*  If you're currently developing using this, you might want to
              leave this break here, because this means something unexpected
              occured. If the break is left in, the server stops running, and
              you should probably look into the exception.

              If you're in production, you shouldn't have this break here,
              because you probably don't want to kill the server...
           */
          break;
        }
      }
    }
    catch (Exception e) {
      /*  Not sure when this occurs, but it might...
       */
      System.err.println("Something bad happened...");
      e.printStackTrace();
    }
    finally {
      try {
        socket.close();
      }
      catch (IOException e) {
        System.err.println("Well that's not good...");
        e.printStackTrace();
      }
    }
  }

  /**
   * Set the {@link HTTPRouter} to determine the what
   * {@link HTTPHandler} will be used.
   *
   * @param router  The HTTPRouter to be used to figure out
   *                        what kind of HTTPHandler we're going to use...
   */
  public void setRouter(HTTPRouter router) {
    HTTPRequest.setRouter(router);
  }

  /**
   * Set information about the server that will be sent through the
   * server header to the client in this format: <p>
   * 
   * <code>{name} v{version} ({etc})</code>
   * @param name The name of your server
   * @param version The version of your server
   * @param etc A message about your server
   */
  public static void setServerInfo(String name, String version, String etc) {
    serverName = name;
    serverVersion = version;
    serverETC = etc;
  }

  /**
   * Gets the server's name.
   * @return The server's name.
   */
  public static String getServerName() {
    return serverName;
  }
  /**
   * Gets the server's version.
   * @return The server's version
   */
  public static String getServerVersion() {
    return serverVersion;
  }
  /**
   * Gets the server's etc information.
   * @return More information about the server.
   */
  public static String getServerETC() {
    return serverETC;
  }

  /**
   * Set the port the server will be listening on.
   * @param port The port the server will use.
   */
  public void setPort(int port) {
    this.port = port;
  }
  /**
   * Get the port the server will be listening on.
   * @return the port number the server is using.
   */
  public int getPort() {
    return port;
  }
}