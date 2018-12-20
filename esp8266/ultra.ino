#define trigPin 13
#define echoPin 0
#define led 16
#define led2 12
#define lamp 4
#define buzzerPin 5
#define fsrPin 0

#include "pitches.h"

#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>
#include <ArduinoJson.h>
#include <ESP8266HTTPClient.h>

#define USE_SERIAL Serial

ESP8266WiFiMulti WiFiMulti;

ESP8266WebServer server(80);

void handleRoot() {
  server.send(200, "text/plain", "ok");
  String body = server.arg("plain");
  DynamicJsonBuffer jsonBuffer(1024);
  JsonObject& root = jsonBuffer.parseObject(body);
  String value = root["data"][0]["status"]["value"];
  //String json = '{"subscriptionId":"5c1ac88dd133c7de45e45fbe","data":[{"id":"lamp1","type":"Thing","status":{"type":"command","value":"off","metadata":{}}}]}'
  Serial.print(value);
  if(value == "on"){
    digitalWrite(lamp, HIGH);
  }else{
    digitalWrite(lamp, LOW);
  }
  
}

void handleNotFound(){
  digitalWrite(led, 1);
  String message = "File Not Found\n\n";
  message += "URI: ";
  message += server.uri();
  message += "\nMethod: ";
  message += (server.method() == HTTP_GET)?"GET":"POST";
  message += "\nArguments: ";
  message += server.args();
  message += "\n";
  for (uint8_t i=0; i<server.args(); i++){
    message += " " + server.argName(i) + ": " + server.arg(i) + "\n";
  }
  server.send(404, "text/plain", message);
  digitalWrite(led, 0);
}

void setup() {
  Serial.begin (9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(led, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(lamp, OUTPUT);
  //pinMode(buzzerPin,OUTPUT);

  WiFi.mode(WIFI_STA);
  WiFiMulti.addAP("Spidi", "pedro123");
  //WiFiMulti.addAP("B313-AE3500", "");

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  if (MDNS.begin("esp8266")) {
    Serial.println("MDNS responder started");
  }

  server.on("/", handleRoot);

  server.on("/inline", [](){
    server.send(200, "text/plain", "this works as well");
  });

  server.onNotFound(handleNotFound);

  server.begin();
  Serial.println("HTTP server started");
  
  updateCrash("false");
}

void updateCrash(String value){
  if ((WiFiMulti.run() == WL_CONNECTED)) {

    Serial.println(WiFi.localIP());

    HTTPClient http;

    Serial.print("[HTTP] begin...\n");
    // configure traged server and url
    //http.begin("https://157.159.16.92:1026/v2", "7a 9c f4 db 40 d3 62 5a 6e 21 bc 5c cc 66 c8 3e a1 45 59 38"); //HTTPS
    String url = "http://192.168.43.221:7896/iot/d?i=sensor_crash&k=123456&d=crashed|"+value;
    http.begin(url); //HTTP

    Serial.print("[HTTP] GET...\n");
    // start connection and send HTTP header
    
    int httpCode = http.GET();

    // httpCode will be negative on error
    if (httpCode > 0) {
      // HTTP header has been send and Server response header has been handled
      Serial.printf("[HTTP] GET... code: %d\n", httpCode);

      // file found at server
      if (httpCode == HTTP_CODE_OK) {
        String payload = http.getString();
        Serial.println(payload);
      }
    } else {
      Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
    }

    http.end();
  }
}

void updateDistance(double value){
  if ((WiFiMulti.run() == WL_CONNECTED)) {

    Serial.println(WiFi.localIP());

    HTTPClient http;

    Serial.print("[HTTP] begin...\n");
    // configure traged server and url
    //http.begin("https://157.159.16.92:1026/v2", "7a 9c f4 db 40 d3 62 5a 6e 21 bc 5c cc 66 c8 3e a1 45 59 38"); //HTTPS
    String url = "http://192.168.43.221:7896/iot/d?i=sensor_distance&k=123456&d=distance|"+String(value);
    http.begin(url); //HTTP

    Serial.print("[HTTP] GET...\n");
    // start connection and send HTTP header
    
    int httpCode = http.GET();

    // httpCode will be negative on error
    if (httpCode > 0) {
      // HTTP header has been send and Server response header has been handled
      Serial.printf("[HTTP] GET... code: %d\n", httpCode);

      // file found at server
      if (httpCode == HTTP_CODE_OK) {
        String payload = http.getString();
        Serial.println(payload);
      }
    } else {
      Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
    }

    http.end();
  }
}

void loop() {
  long duration, distance;
  int fsrReading;
  digitalWrite(trigPin, LOW);  // Added this line
  delayMicroseconds(2); // Added this line
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10); // Added this line
  digitalWrite(trigPin, LOW);

  duration = pulseIn(echoPin, HIGH);
  distance = (duration / 2) / 29.1;

  fsrReading = analogRead(fsrPin);

  Serial.print("Analog reading = ");
  Serial.print(fsrReading);     // the raw analog reading

  // We'll have a few threshholds, qualitatively determined
  if (fsrReading < 100) {
    digitalWrite(led2, LOW);
    Serial.println(" - No pressure");
  } else if (fsrReading >= 100 && fsrReading < 800) {
    digitalWrite(led2, LOW);
    Serial.println(" - Low pressure");
    updateCrash("false");
  } else {
    digitalWrite(led2, HIGH);
    updateCrash("true");
    Serial.println(" - Big squeeze");
  }


  if (distance > 1 && distance < 5) {
    digitalWrite(led, HIGH);
    tone(5, 440, 500);
    //delay(50);
  }
  if (distance > 5 && distance < 10) {
    digitalWrite(led, HIGH);
    tone(5, 440, 50);
    delay(100);
    digitalWrite(led, LOW);
  }
  else if (distance > 10 && distance < 15) {
    digitalWrite(led, HIGH);
    tone(5, 440, 50);
    delay(150);
    digitalWrite(led, LOW);
  }
  else if (distance > 15 && distance < 20) {
    digitalWrite(led, HIGH);
    tone(5, 440, 50);
    delay(200);
    digitalWrite(led, LOW);
  }
  else if (distance > 20 && distance < 25) {
    digitalWrite(led, HIGH);
    tone(5, 440, 50);
    delay(250);
    digitalWrite(led, LOW);
  }
  else if (distance > 25 && distance < 30) {
    digitalWrite(led, HIGH);
    tone(5, 440, 50);
    delay(300);
    digitalWrite(led, LOW);
  }
  else if (distance > 30 && distance < 35) {
    digitalWrite(led, HIGH);
    tone(5, 440, 50);
    delay(350);
    digitalWrite(led, LOW);
  }
  else if (distance > 35 && distance < 40) {
    digitalWrite(led, HIGH);
    tone(5, 440, 50);
    delay(400);
    digitalWrite(led, LOW);
  }
  else if (distance > 40 && distance < 45) {
    digitalWrite(led, HIGH);
    tone(5, 440, 50);
    delay(450);
    digitalWrite(led, LOW);
  }
  else if (distance > 45 && distance < 50) {
    digitalWrite(led, HIGH);
    tone(5, 440, 50);
    delay(500);
    digitalWrite(led, LOW);
  }
  else if (distance > 50 && distance < 55) {
    digitalWrite(led, HIGH);
    tone(5, 440, 50);
    delay(550);
    digitalWrite(led, LOW);
  }
  else if (distance > 55 && distance < 60) {
    digitalWrite(led, HIGH);
    tone(5, 440, 50);
    delay(600);
    digitalWrite(led, LOW);
  }
  else {
    digitalWrite(led, LOW);
    delay(500);
  }

  if (distance >= 60 || distance <= 0) {
    Serial.println("Out of range");
  }
  else {
    updateDistance(distance);
    Serial.print(distance);
    Serial.println(" cm");
  }

  server.handleClient();

}

