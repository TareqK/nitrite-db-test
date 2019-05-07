/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tareq.nitrite.testing.entity;

import lombok.Data;

/**
 *
 * @author tareq
 */
@Data
public class TestEntity {

  private int thing;
  private EmbeddedEntity embed;
}
