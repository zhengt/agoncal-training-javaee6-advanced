package org.agoncal.training.javaee6adv.view;

import org.agoncal.training.javaee6adv.model.Publisher;
import org.agoncal.training.javaee6adv.service.AbstractService;
import org.agoncal.training.javaee6adv.service.PublisherService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class PublisherBeanTest
{

   @Inject
   private PublisherBean publisherbean;

   @Deployment
   public static JavaArchive createDeployment()
   {
      return ShrinkWrap.create(JavaArchive.class)
            .addClass(PublisherBean.class)
            .addClass(AbstractService.class)
            .addClass(PublisherService.class)
            .addClass(Publisher.class)
            .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
   }

   @Test
   public void should_be_deployed()
   {
      assertNotNull(publisherbean);
   }

   @Test
   public void should_crud()
   {
      // Creates an object
      Publisher publisher = new Publisher();
      publisher.setName("Dummy value");

      // Inserts the object into the database
      publisherbean.setPublisher(publisher);
      publisherbean.create();
      publisherbean.update();
      publisher = publisherbean.getPublisher();
      assertNotNull(publisher.getId());

      // Finds the object from the database and checks it's the right one
      publisher = publisherbean.findById(publisher.getId());
      assertEquals("Dummy value", publisher.getName());

      // Deletes the object from the database and checks it's not there anymore
      publisherbean.setId(publisher.getId());
      publisherbean.create();
      publisherbean.delete();
      publisher = publisherbean.findById(publisher.getId());
      assertNull(publisher);
   }

   @Test
   public void should_paginate()
   {
      // Creates an empty example
      Publisher example = new Publisher();

      // Paginates through the example
      publisherbean.setExample(example);
      publisherbean.paginate();
      assertTrue((publisherbean.getPageItems().size() == publisherbean.getPageSize()) || (publisherbean.getPageItems().size() == publisherbean.getCount()));
   }
}
