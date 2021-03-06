package com.balancedpayments.android;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import junit.framework.TestCase;

import com.balancedpayments.android.Card;
import com.balancedpayments.android.Card.CardType;

public class CardTest extends TestCase {

   public void testIsValidWithValidCardNumbers() {
      String[] cardNumbers = { "4111111111111111",
                               "4444444444444448",
                               "4222222222222220",
                               "4532418643138442",
                               "4716314539050650",
                               "4485498805067453",
                               "4929679978342120",
                               "4400544701105053",
                               "5105105105105100",
                               "5549904348586207",
                               "5151601696648220",
                               "5421885505663975",
                               "5377756349885534",
                               "5346784314486086",
                               "6011373997942482",
                               "6011640053409402",
                               "6011978682866778",
                               "6011391946659189",
                               "6011358300105877" };
      Calendar cal = Calendar.getInstance();
      int month = cal.get(Calendar.MONTH) + 1;
      int year = cal.get(Calendar.YEAR);

      HashMap<String, Object> optionalFields = new HashMap<String, Object>();
      optionalFields.put("cvv", "123");

      for (int i = 0; i < cardNumbers.length; i++) {
         Card card = new Card(cardNumbers[i], month, year, optionalFields);

         assertTrue(card.isValid());
         assertTrue(card.getErrors().isEmpty());
      }
   }

   public void testIsValidWithValidAmericanExpressCardNumbers() {
      String[] cardNumbers = { "341111111111111",
                               "340893849936650",
                               "372036201733247",
                               "378431622693837",
                               "346313453954711",
                               "341677236686203" };
      Calendar cal = Calendar.getInstance();
      int month = cal.get(Calendar.MONTH) + 1;
      int year = cal.get(Calendar.YEAR);

      HashMap<String, Object> optionalFields = new HashMap<String, Object>();
      optionalFields.put("cvv", "1234");

      for (int i = 0; i < cardNumbers.length; i++) {
         Card card = new Card(cardNumbers[i], month, year, optionalFields);

         assertTrue(card.isValid());
         assertTrue(card.getErrors().isEmpty());
      }
   }

   public void testIsValidWithCardNumberNull() {
      String cardNumber = null;

      HashMap<String, Object> optionalFields = new HashMap<String, Object>();
      optionalFields.put("cvv", "123");

      Calendar cal = Calendar.getInstance();
      int month = cal.get(Calendar.MONTH);
      int year = cal.get(Calendar.YEAR);

      Card card = new Card(cardNumber, month, year, optionalFields);

      assertFalse(card.isValid());
      assertFalse(card.getErrors().isEmpty());
      assertTrue(card.getErrors().contains("Card number is not valid"));
   }

   public void testIsValidWithCardNumberBlank() {
      String cardNumber = "";

      HashMap<String, Object> optionalFields = new HashMap<String, Object>();
      optionalFields.put("cvv", "123");

      Calendar cal = Calendar.getInstance();
      int month = cal.get(Calendar.MONTH);
      int year = cal.get(Calendar.YEAR);

      Card card = new Card(cardNumber, month, year, optionalFields);

      assertFalse(card.isValid());
      assertFalse(card.getErrors().isEmpty());
      assertTrue(card.getErrors().contains("Card number is not valid"));
   }

   public void testIsValidWithInvalidWithExpiresThisMonth() {
      String cardNumber = "4242424242424242";
      HashMap<String, Object> optionalFields = new HashMap<String, Object>();
      optionalFields.put("cvv", "123");
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      int month = cal.get(Calendar.MONTH);
      int year = cal.get(Calendar.YEAR);

      Card card = new Card(cardNumber, month, year, optionalFields);

      assertFalse(card.isValid());
      assertFalse(card.getErrors().isEmpty());
      assertTrue(card.getErrors().contains("Card is expired"));
   }

   public void testIsValidWithInvalidWithExpiredLastMonth() {
      String cardNumber = "4242424242424242";
      HashMap<String, Object> optionalFields = new HashMap<String, Object>();
      optionalFields.put("cvv", "123");
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      int month = cal.get(Calendar.MONTH) - 1;
      int year = cal.get(Calendar.YEAR);

      Card card = new Card(cardNumber, month, year, optionalFields);

      assertFalse(card.isValid());
      assertFalse(card.getErrors().isEmpty());
      assertTrue(card.getErrors().contains("Card is expired"));
   }

   public void testIsValidWithInvalidWithExpiredYear() {
      String cardNumber = "4242424242424242";
      HashMap<String, Object> optionalFields = new HashMap<String, Object>();
      optionalFields.put("cvv", "123");
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      int month = cal.get(Calendar.MONTH);
      int year = cal.get(Calendar.YEAR) - 1;

      Card card = new Card(cardNumber, month, year, optionalFields);

      assertFalse(card.isValid());
      assertFalse(card.getErrors().isEmpty());
      assertTrue(card.getErrors().contains("Card is expired"));
   }

   public void testCardTypeVisa() {
      String cardNumber = "4242424242424242";
      HashMap<String, Object> optionalFields = new HashMap<String, Object>();
      optionalFields.put("cvv", "123");
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      int month = cal.get(Calendar.MONTH) + 1;
      int year = cal.get(Calendar.YEAR) + 1;

      Card card = new Card(cardNumber, month, year, optionalFields);

      assertTrue(card.isValid());
      assertTrue(card.getErrors().isEmpty());
      assertTrue(card.getType().equals(CardType.VISA));
   }

   public void testCardTypeMastercard() {
      String cardNumber = "5377756349885534";
      HashMap<String, Object> optionalFields = new HashMap<String, Object>();
      optionalFields.put("cvv", "123");
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      int month = cal.get(Calendar.MONTH) + 1;
      int year = cal.get(Calendar.YEAR) + 1;

      Card card = new Card(cardNumber, month, year, optionalFields);

      assertTrue(card.isValid());
      assertTrue(card.getErrors().isEmpty());
      assertTrue(card.getType().equals(CardType.MASTERCARD));
   }

   public void testCardTypeAmericanExpress() {
      String cardNumber = "341111111111111";
      HashMap<String, Object> optionalFields = new HashMap<String, Object>();
      optionalFields.put("cvv", "1234");
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      int month = cal.get(Calendar.MONTH) + 1;
      int year = cal.get(Calendar.YEAR) + 1;

      Card card = new Card(cardNumber, month, year, optionalFields);

      assertTrue(card.getType().equals(CardType.AMERICANEXPRESS));
      assertTrue(card.isValid());
      assertTrue(card.getErrors().isEmpty());
   }

   public void testCardTypeDiscover() {
      String cardNumber = "6011640053409402";
      HashMap<String, Object> optionalFields = new HashMap<String, Object>();
      optionalFields.put("cvv", "123");
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      int month = cal.get(Calendar.MONTH) + 1;
      int year = cal.get(Calendar.YEAR) + 1;

      Card card = new Card(cardNumber, month, year, optionalFields);

      assertTrue(card.isValid());
      assertTrue(card.getErrors().isEmpty());
      assertTrue(card.getType().equals(CardType.DISCOVER));
   }
}
