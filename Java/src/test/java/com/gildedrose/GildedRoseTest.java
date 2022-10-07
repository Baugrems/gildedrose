package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GildedRoseTest {

//    @Test
//    void foo() {
//        Item[] items = new Item[] { new Item("foo", 0, 0) };
//        GildedRose app = new GildedRose(items);
//        app.updateQuality();
//        assertEquals("fixme", app.items[0].name);
//    }


    /*
    Test for if a basic item updates properly once
     */
    @Test
    void testBasicQualityUpdate() {
        Item[] items = new Item[] {
            new Item("test item", 10, 10)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].quality);
    }

    /*
    Test that nothing goes above 50 or below 0 (not including legendary)
     */
    @Test
    void testNegativesAndOverflows() {
        Item[] items = new Item[] {
            new Item("test item", 10, 10),
            new Item("Aged Brie", 10, 2),
            new Item("Backstage passes to a TAFKAL80ETC concert", 25, 10),
            new Item("Aged Brie", 1, 40),
            new Item("Steel Sword", 10, 34),
            new Item("Steel Helmet", 5, 18),
        };
        GildedRose app = new GildedRose(items);
        for(int i=0;i<50;i++){
            app.updateQuality();
        }
        for(Item item: app.items) {
            assertFalse(item.quality > 50 || item.quality < 0);
        }
    }

    /*
    Make sure quality cannot go below 0
     */
    @Test
    void testMinQualityUpdate() {
        Item[] items = new Item[] {
            new Item("test item", 10, 1)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    /*
    Test to see if after many days, quality of basic item decreases properly
     */
    @Test
    void testMultipleDayQualityUpdate() {
        Item[] items = new Item[] {
            new Item("test item", 10, 10)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        app.updateQuality();
        assertEquals(8, app.items[0].quality);
        app.updateQuality();
        app.updateQuality();
        app.updateQuality();
        app.updateQuality();
        assertEquals(4, app.items[0].quality);
    }

    /*
    Make sure entire list of items is addressed with update quality
     */
    @Test
    void testManyItemsUpdated() {
        Item[] items = new Item[] {
            new Item("test item1", 10, 10),
            new Item("test item2", 10, 10),
            new Item("test item3", 10, 10),
            new Item("test item4", 10, 10),
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for(Item item: app.items) {
            assertEquals(9, item.quality);
        }
    }

    /*
    Make sure Aged Brie increases in quality
     */
    @Test
    void testBrieQuality() {
        Item[] items = new Item[] {
            new Item("Aged Brie", 10, 2)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(3, app.items[0].quality);
    }

    /*
    Make sure Aged Brie does not go over 50 quality
     */
    @Test
    void testBrieMaxQuality() {
        Item[] items = new Item[] {
            new Item("Aged Brie", 10, 49)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        app.updateQuality();
        app.updateQuality();
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    /*
    Make sure Aged Brie does not go over 50 quality, even when going up by 2 post sell-date
     */
    @Test
    void testBrieEdgeMaxQuality() {
        Item[] items = new Item[] {
            new Item("Aged Brie", 1, 46)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        app.updateQuality();
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    /*
  Test for if a basic item decreases quality by 2 beyond sell date
   */
    @Test
    void testBasicExpiredQualityUpdate() {
        Item[] items = new Item[] {
            new Item("test item", 1, 10)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].quality);
        app.updateQuality();
        assertEquals(7, app.items[0].quality);
    }


    /*
    Test legendary item never changing quality
     */
    @Test
    void testLegendaryItemUpdate() {
        Item[] items = new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", 1, 80)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
    }

    /*
    Test legendary item never changing quality
     */
    @Test
    void testPassesBasic() {
        Item[] items = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 10)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        // Get to 10 days, increment by 1 quality
        assertEquals(11, app.items[0].quality);
        for(int i=0;i<4;i++){
            app.updateQuality();
        }
        assertEquals(15, app.items[0].quality);
        //Now with 10 days or less, increase by 2 per day
        app.updateQuality();
        assertEquals(17, app.items[0].quality);
        for(int i=0;i<4;i++){
            app.updateQuality();
        }
        assertEquals(25, app.items[0].quality);
        // 5 days or less, increase by 3 per day
        app.updateQuality();
        assertEquals(28, app.items[0].quality);
        for(int i=0;i<4;i++){
            app.updateQuality();
        }
        assertEquals(40, app.items[0].quality);
        app.updateQuality();
        // Concert is passed, quality should be 0 now.
        assertEquals(0, app.items[0].quality);
    }


}
