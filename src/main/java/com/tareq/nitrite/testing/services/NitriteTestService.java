/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tareq.nitrite.testing.services;

import com.tareq.nitrite.testing.entity.TestEntity;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.Getter;
import lombok.Setter;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

/**
 *
 * @author tareq
 */
@Path("test")
public class NitriteTestService {

  @Getter
  @Setter
  private static final Nitrite DB;

  static {
    DB = Nitrite.builder().filePath("test.db").openOrCreate();
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public void addDocument(Document doc) {
    DB.getCollection("test").insert(doc);
  }

  @POST
  @Path("search")
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public List<Document> searchDocument(Document doc) {
    String key = doc.get("key", String.class);
    Object value = doc.get("value");
    return DB.getCollection("test").find(eq(key, value)).toList();
  }

  @POST
  @Path("entity")
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public void addEntity(TestEntity doc) {
    DB.getRepository(TestEntity.class).insert(doc);
  }

  @POST
  @Path("entity/search")
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public List<TestEntity> searchEntity(Document doc) {
    String key = doc.get("key", String.class);
    Object value = doc.get("value");
    return DB.getRepository(TestEntity.class).find(eq(key, value)).toList();
  }
}
