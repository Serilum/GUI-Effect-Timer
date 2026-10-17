package com.serilum.guieffecttimer.config;

import com.natamus.collective.config.DuskConfig;
import com.serilum.guieffecttimer.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry(rgb = true) public static String timerColourRGB = "255,255,255";

	public static void initConfig() {
		configMetaData.put("timerColourRGB", Arrays.asList(
			"The colour of the timer text, as red, green and blue values from 0 to 255."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}