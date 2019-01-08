#include <ESP8266WiFi.h>

const char* ssid = "Jara4G";
const char* password = "!tsJ@r@B@bY";

WiFiServer server(80);

void setup() {    
  Serial.begin(115200);
  pinMode(A0, OUTPUT);
  delay(10);  
  
  // Connect to WiFi network
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  
  // Start the server
  server.begin();
  Serial.println("Server started");

  // Print the IP address
  Serial.println(WiFi.localIP());
}

void loop() {       
  // Check if a client has connected
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
  
  // Wait until the client sends some data
  Serial.println("new client");
  while(!client.available()){
    delay(1);
  }
  
  // Read the first line of the request
  String req = client.readStringUntil('\r');
  Serial.println(req);
  client.flush();
  
  // Match the request
  String s;
  float sensorValue = 0;  
  float vol = 0;
  float ec = 0;  
    
  if (req.indexOf("/read") != -1){
    sensorValue = analogRead(A0);

    vol = sensorValue * (5.0 / 1023.0);
    ec = vol*10000; //EC value in us/cm
  
    Serial.println("EC value : " + String(ec,2));
   
    s = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + String(ec,2);    
  }    
  else {
    Serial.println("invalid request");
    client.stop();
    return;
  }
  
  client.flush();

  // Send the response to the client
  client.print(s);
  delay(1);
  Serial.println("Client disonnected");

  // The client will actually be disconnected 
  // when the function returns and 'client' object is detroyed

}
