/**
 * @author Edouard DUPIN
 * 
 * @copyright 2011, Edouard DUPIN, all right reserved
 * 
 * @license MPL v2.0 (see license file)
 */

#include <etest/etest.hpp>

#include <etk/Map.hpp>
#include <test-debug/debug.hpp>
#define NAME "Map"

TEST(TestEtkMap, Creation) {
	etk::Map<uint32_t, etk::String> testData(0,true);
	EXPECT_EQ(testData.size(), 0);
}

TEST(TestEtkMap, add_ordered) {
	etk::Map<uint32_t, etk::String> testData(0,true);
	EXPECT_EQ(testData.size(), 0);
	testData.add(2, "a 2");
	testData.add(19, "b 19");
	testData.add(1, "c 1");
	testData.add(5, "d 5");
	testData.add(66, "e 66");
	EXPECT_EQ(testData.size(), 5);
	EXPECT_EQ(testData[1], "c 1");
	EXPECT_EQ(testData.getValue(0), "c 1");
}
/*
TEST(TestEtkMap, initialization_list) {
	etk::Map<uint32_t, etk::String> testData({uint32_t(12),etk::String("12")},{uint32_t(11),etk::String("11")});
	EXPECT_EQ(testData.size(), 2);
	EXPECT_EQ(testData[11], "11");
	EXPECT_EQ(testData[12], "12");
	EXPECT_EQ(testData.getValue(0), "11");
	EXPECT_EQ(testData.getValue(1), "12");
}

TEST(TestEtkMap, initialization_list_2) {
	etk::Map<uint32_t, etk::String> testData = {{12,"12"},{11,"11"}};
	EXPECT_EQ(testData.size(), 2);
	EXPECT_EQ(testData[11], "11");
	EXPECT_EQ(testData[12], "12");
	EXPECT_EQ(testData.getValue(0), "11");
	EXPECT_EQ(testData.getValue(1), "12");
}
*/
