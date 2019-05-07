/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tareq.nitrite.testing.test;

import com.tareq.nitrite.testing.config.Application;
import com.tareq.nitrite.testing.entity.EmbeddedEntity;
import com.tareq.nitrite.testing.entity.TestEntity;
import com.tareq.nitrite.testing.services.NitriteTestService;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.dizitart.no2.Document;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author tareq
 */
public class SearchTest extends JerseyTest {

  @Override
  protected ResourceConfig configure() {
    return new Application();
  }

  @Override
  protected void configureClient(ClientConfig clientConfig) {
    clientConfig.register(new JacksonFeature());
  }

  @Test
  public void insertDocByJerseyAndSearchTest() {
    Document doc = new Document()
      .put("test", "something")
      .put("embed", new Document()
        .put("firstEmbed", 123)
        .put("secondEmbed", "test"));
    Response resp = target("test")
      .request()
      .post(Entity.entity(doc, MediaType.APPLICATION_JSON));

    assertEquals(204, resp.getStatus());

    Document search = new Document()
      .put("key", "embed.firstEmbed")
      .put("value", 123);

    Response searchResp = target("test")
      .path("search")
      .request()
      .post(Entity.entity(search, MediaType.APPLICATION_JSON));
    try {
      assertNotEquals(500, searchResp.getStatus());
    } catch (AssertionError ex) {
      System.out.println("Search Response Status : " + searchResp.getStatus());
      System.out.println("Search Response Was : " + searchResp.readEntity(String.class));
      throw ex;
    }
    assertNotEquals(0, searchResp.readEntity(List.class).size());

  }

  @Test
  public void insertDocByDBAndSearchByJersey() {
    Document doc = new Document()
      .put("test", "something")
      .put("embed", new Document()
        .put("firstEmbed", 123)
        .put("secondEmbed", "test"));

    NitriteTestService.getDB().getCollection("test").insert(doc);

    Document search = new Document()
      .put("key", "embed.firstEmbed")
      .put("value", 123);

    Response searchResp = target("test")
      .path("search")
      .request()
      .post(Entity.entity(search, MediaType.APPLICATION_JSON));
    try {
      assertNotEquals(500, searchResp.getStatus());
    } catch (AssertionError ex) {
      System.out.println("Search Response Status : " + searchResp.getStatus());
      System.out.println("Search Response Was : " + searchResp.readEntity(String.class));
      throw ex;
    }
    assertNotEquals(0, searchResp.readEntity(List.class).size());
  }

  @Test
  public void insertDocByDBAndSearchByDB() {
    Document doc = new Document()
      .put("test", "something")
      .put("embed", new Document()
        .put("firstEmbed", 123)
        .put("secondEmbed", "test"));
    try {
      NitriteTestService.getDB().getCollection("test").insert(doc);
      NitriteTestService.getDB().getCollection("test").find(eq("embed.firstEmbed", 123));
    } catch (Exception ex) {
      fail("A Nitrite Exception Has Occured");
    }
  }

  @Test
  public void insertEntityByJerseyAndSearchTest() {
    TestEntity test = new TestEntity();
    EmbeddedEntity embed = new EmbeddedEntity();
    embed.setFirstEmbed(123);
    embed.setSecondEmbed("teasdasdsadsad");
    test.setThing(333);
    test.setEmbed(embed);

    Response resp = target("test")
      .path("entity")
      .request()
      .post(Entity.entity(test, MediaType.APPLICATION_JSON));

    assertEquals(204, resp.getStatus());

    Document search = new Document()
      .put("key", "embed.firstEmbed")
      .put("value", 123);

    Response searchResp = target("test")
      .path("entity")
      .path("search")
      .request()
      .post(Entity.entity(search, MediaType.APPLICATION_JSON));
    try {
      assertNotEquals(500, searchResp.getStatus());
    } catch (AssertionError ex) {
      System.out.println("Search Response Status : " + searchResp.getStatus());
      System.out.println("Search Response Was : " + searchResp.readEntity(String.class));
      throw ex;
    }
    assertNotEquals(0, searchResp.readEntity(List.class).size());

  }

  @Test
  public void insertEntityByDBAndSearchByJersey() {
    TestEntity test = new TestEntity();
    EmbeddedEntity embed = new EmbeddedEntity();
    embed.setFirstEmbed(123);
    embed.setSecondEmbed("teasdasdsadsad");
    test.setThing(333);
    test.setEmbed(embed);

    NitriteTestService.getDB().getRepository(TestEntity.class).insert(test);

    Document search = new Document()
      .put("key", "embed.firstEmbed")
      .put("value", 123);

    Response searchResp = target("test")
      .path("entity")
      .path("search")
      .request()
      .post(Entity.entity(search, MediaType.APPLICATION_JSON));
    try {
      assertNotEquals(500, searchResp.getStatus());
    } catch (AssertionError ex) {
      System.out.println("Search Response Status : " + searchResp.getStatus());
      System.out.println("Search Response Was : " + searchResp.readEntity(String.class));
      throw ex;
    }
    assertNotEquals(0, searchResp.readEntity(List.class).size());
  }

  @Test
  public void insertEntityByDBAndSearchByDB() {
    TestEntity test = new TestEntity();
    EmbeddedEntity embed = new EmbeddedEntity();
    embed.setFirstEmbed(123);
    embed.setSecondEmbed("teasdasdsadsad");
    test.setThing(333);
    test.setEmbed(embed);
    try {
      NitriteTestService.getDB().getRepository(TestEntity.class).insert(test);
      NitriteTestService.getDB().getRepository(TestEntity.class).find(eq("embed.firstEmbed", 123));
    } catch (Exception ex) {
      fail("A Nitrite Exception Has Occured");
    }
  }

}
