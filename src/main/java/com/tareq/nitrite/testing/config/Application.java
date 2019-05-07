/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tareq.nitrite.testing.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author tareq
 */
public class Application extends ResourceConfig  {
  
  public Application(){
    packages("com.tareq.nitrite.testing.services")
      .register(new JacksonFeature());
  }
  
}
