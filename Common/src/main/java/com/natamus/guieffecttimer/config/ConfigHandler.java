package com.natamus.guieffecttimer.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.guieffecttimer.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry(min = 0, max = 255) public static int timerColourRGB_R = 255;
	@Entry(min = 0, max = 255) public static int timerColourRGB_G = 255;
	@Entry(min = 0, max = 255) public static int timerColourRGB_B = 255;

	public static void initConfig() {
		configMetaData.put("timerColourRGB_R", Arrays.asList(
			"The red RGB value for the timer text."
		));
		configMetaData.put("timerColourRGB_G", Arrays.asList(
			"The green RGB value for the timer text."
		));
		configMetaData.put("timerColourRGB_B", Arrays.asList(
			"The blue RGB value for the timer text."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}