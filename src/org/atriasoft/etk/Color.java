package org.atriasoft.etk;

import java.util.Map;

import org.atriasoft.etk.math.FMath;

@SuppressWarnings("preview")

//@formatter:off
public record Color(
		float r,
		float g,
		float b,
		float a) {
//@formatter:on
	public static final Color NONE = new Color(0x00, 0x00, 0x00, 0x00);
	public static final Color ALICE_BLUE = new Color(0xF0, 0xF8, 0xFF, 0xFF);
	public static final Color ANTIQUE_WHITE = new Color(0xFA, 0xEB, 0xD7, 0xFF);
	public static final Color AQUA = new Color(0x00, 0xFF, 0xFF, 0xFF);
	public static final Color AQUA_MARINE = new Color(0x7F, 0xFF, 0xD4, 0xFF);
	public static final Color AZURE = new Color(0xF0, 0xFF, 0xFF, 0xFF);
	public static final Color BEIGE = new Color(0xF5, 0xF5, 0xDC, 0xFF);
	public static final Color BISQUE = new Color(0xFF, 0xE4, 0xC4, 0xFF);
	public static final Color BLACK = new Color(0x00, 0x00, 0x00, 0xFF);
	public static final Color BLANCHED_ALMOND = new Color(0xFF, 0xEB, 0xCD, 0xFF);
	public static final Color BLUE = new Color(0x00, 0x00, 0xFF, 0xFF);
	public static final Color BLUE_VIOLET = new Color(0x8A, 0x2B, 0xE2, 0xFF);
	public static final Color BROWN = new Color(0xA5, 0x2A, 0x2A, 0xFF);
	public static final Color BURLY_WOOD = new Color(0xDE, 0xB8, 0x87, 0xFF);
	public static final Color CADET_BLUE = new Color(0x5F, 0x9E, 0xA0, 0xFF);
	public static final Color CHARTREUSE = new Color(0x7F, 0xFF, 0x00, 0xFF);
	public static final Color CHOCOLATE = new Color(0xD2, 0x69, 0x1E, 0xFF);
	public static final Color CORAL = new Color(0xFF, 0x7F, 0x50, 0xFF);
	public static final Color CORNFLOWER_BLUE = new Color(0x64, 0x95, 0xED, 0xFF);
	public static final Color CORNSILK = new Color(0xFF, 0xF8, 0xDC, 0xFF);
	public static final Color CRIMSON = new Color(0xDC, 0x14, 0x3C, 0xFF);
	public static final Color CYAN = new Color(0x00, 0xFF, 0xFF, 0xFF);
	public static final Color DARK_BLUE = new Color(0x00, 0x00, 0x8B, 0xFF);
	public static final Color DARK_CYAN = new Color(0x00, 0x8B, 0x8B, 0xFF);
	public static final Color DARK_GOLDENROD = new Color(0xB8, 0x86, 0x0B, 0xFF);
	public static final Color DARK_GRAY = new Color(0xA9, 0xA9, 0xA9, 0xFF);
	public static final Color DARK_GREY = new Color(0xA9, 0xA9, 0xA9, 0xFF);
	public static final Color DARK_GREEN = new Color(0x00, 0x64, 0x00, 0xFF);
	public static final Color DARK_KHAKI = new Color(0xBD, 0xB7, 0x6B, 0xFF);
	public static final Color DARK_MAGENTA = new Color(0x8B, 0x00, 0x8B, 0xFF);
	public static final Color DARK_OLIVEGREEN = new Color(0x55, 0x6B, 0x2F, 0xFF);
	public static final Color DARK_ORANGE = new Color(0xFF, 0x8C, 0x00, 0xFF);
	public static final Color DARK_ORCHID = new Color(0x99, 0x32, 0xCC, 0xFF);
	public static final Color DARK_RED = new Color(0x8B, 0x00, 0x00, 0xFF);
	public static final Color DARK_SALMON = new Color(0xE9, 0x96, 0x7A, 0xFF);
	public static final Color DARK_SEAGREEN = new Color(0x8F, 0xBC, 0x8F, 0xFF);
	public static final Color DARK_SLATE_BLUE = new Color(0x48, 0x3D, 0x8B, 0xFF);
	public static final Color DARK_SLATE_GRAY = new Color(0x2F, 0x4F, 0x4F, 0xFF);
	public static final Color DARK_SLATE_GREY = new Color(0x2F, 0x4F, 0x4F, 0xFF);
	public static final Color DARK_TURQUOISE = new Color(0x00, 0xCE, 0xD1, 0xFF);
	public static final Color DARK_VIOLET = new Color(0x94, 0x00, 0xD3, 0xFF);
	public static final Color DEEP_PINK = new Color(0xFF, 0x14, 0x93, 0xFF);
	public static final Color DEEP_SKY_BLUE = new Color(0x00, 0xBF, 0xFF, 0xFF);
	public static final Color DIM_GRAY = new Color(0x69, 0x69, 0x69, 0xFF);
	public static final Color DIM_GREY = new Color(0x69, 0x69, 0x69, 0xFF);
	public static final Color DODGER_BLUE = new Color(0x1E, 0x90, 0xFF, 0xFF);
	public static final Color FIRE_BRICK = new Color(0xB2, 0x22, 0x22, 0xFF);
	public static final Color FLORAL_WHITE = new Color(0xFF, 0xFA, 0xF0, 0xFF);
	public static final Color FOREST_GREEN = new Color(0x22, 0x8B, 0x22, 0xFF);
	public static final Color FUCHSIA = new Color(0xFF, 0x00, 0xFF, 0xFF);
	public static final Color GAINSBORO = new Color(0xDC, 0xDC, 0xDC, 0xFF);
	public static final Color GHOST_WHITE = new Color(0xF8, 0xF8, 0xFF, 0xFF);
	public static final Color GOLD = new Color(0xFF, 0xD7, 0x00, 0xFF);
	public static final Color GOLDEN_ROD = new Color(0xDA, 0xA5, 0x20, 0xFF);
	public static final Color GRAY = new Color(0x80, 0x80, 0x80, 0xFF);
	public static final Color GREY = new Color(0x80, 0x80, 0x80, 0xFF);
	public static final Color GREEN = new Color(0x00, 0x80, 0x00, 0xFF);
	public static final Color GREEN_YELLOW = new Color(0xAD, 0xFF, 0x2F, 0xFF);
	public static final Color HONEY_DEW = new Color(0xF0, 0xFF, 0xF0, 0xFF);
	public static final Color HOT_PINK = new Color(0xFF, 0x69, 0xB4, 0xFF);
	public static final Color INDIAN_RED = new Color(0xCD, 0x5C, 0x5C, 0xFF);
	public static final Color INDIGO = new Color(0x4B, 0x00, 0x82, 0xFF);
	public static final Color IVORY = new Color(0xFF, 0xFF, 0xF0, 0xFF);
	public static final Color KHAKI = new Color(0xF0, 0xE6, 0x8C, 0xFF);
	public static final Color LAVENDER = new Color(0xE6, 0xE6, 0xFA, 0xFF);
	public static final Color LAVENDER_BLUSH = new Color(0xFF, 0xF0, 0xF5, 0xFF);
	public static final Color LAWN_GREEN = new Color(0x7C, 0xFC, 0x00, 0xFF);
	public static final Color LEMON_CHIFFON = new Color(0xFF, 0xFA, 0xCD, 0xFF);
	public static final Color LIGHT_BLUE = new Color(0xAD, 0xD8, 0xE6, 0xFF);
	public static final Color LIGHT_CORAL = new Color(0xF0, 0x80, 0x80, 0xFF);
	public static final Color LIGHT_CYAN = new Color(0xE0, 0xFF, 0xFF, 0xFF);
	public static final Color LIGHT_GOLDEN_ROD_YELLOW = new Color(0xFA, 0xFA, 0xD2, 0xFF);
	public static final Color LIGHT_GRAY = new Color(0xD3, 0xD3, 0xD3, 0xFF);
	public static final Color LIGHT_GREY = new Color(0xD3, 0xD3, 0xD3, 0xFF);
	public static final Color LIGHT_GREEN = new Color(0x90, 0xEE, 0x90, 0xFF);
	public static final Color LIGHT_PINK = new Color(0xFF, 0xB6, 0xC1, 0xFF);
	public static final Color LIGHT_SALMON = new Color(0xFF, 0xA0, 0x7A, 0xFF);
	public static final Color LIGHT_SEA_GREEN = new Color(0x20, 0xB2, 0xAA, 0xFF);
	public static final Color LIGHT_SKY_BLUE = new Color(0x87, 0xCE, 0xFA, 0xFF);
	public static final Color LIGHT_SLATE_GRAY = new Color(0x77, 0x88, 0x99, 0xFF);
	public static final Color LIGHT_SLATE_GREY = new Color(0x77, 0x88, 0x99, 0xFF);
	public static final Color LIGHT_STEEL_BLUE = new Color(0xB0, 0xC4, 0xDE, 0xFF);
	public static final Color LIGHT_YELLOW = new Color(0xFF, 0xFF, 0xE0, 0xFF);
	public static final Color LIME = new Color(0x00, 0xFF, 0x00, 0xFF);
	public static final Color LIME_GREEN = new Color(0x32, 0xCD, 0x32, 0xFF);
	public static final Color LINEN = new Color(0xFA, 0xF0, 0xE6, 0xFF);
	public static final Color MAGENTA = new Color(0xFF, 0x00, 0xFF, 0xFF);
	public static final Color MAROON = new Color(0x80, 0x00, 0x00, 0xFF);
	public static final Color MEDIUM_AQUA_MARINE = new Color(0x66, 0xCD, 0xAA, 0xFF);
	public static final Color MEDIUM_BLUE = new Color(0x00, 0x00, 0xCD, 0xFF);
	public static final Color MEDIUM_ORCHID = new Color(0xBA, 0x55, 0xD3, 0xFF);
	public static final Color MEDIUM_PURPLE = new Color(0x93, 0x70, 0xD8, 0xFF);
	public static final Color MEDIUM_SEA_GREEN = new Color(0x3C, 0xB3, 0x71, 0xFF);
	public static final Color MEDIUM_SLATE_BLUE = new Color(0x7B, 0x68, 0xEE, 0xFF);
	public static final Color MEDIUM_SPRING_GREEN = new Color(0x00, 0xFA, 0x9A, 0xFF);
	public static final Color MEDIUM_TURQUOISE = new Color(0x48, 0xD1, 0xCC, 0xFF);
	public static final Color MEDIUM_VIOLET_RED = new Color(0xC7, 0x15, 0x85, 0xFF);
	public static final Color MIDNIGHT_BLUE = new Color(0x19, 0x19, 0x70, 0xFF);
	public static final Color MINT_CREAM = new Color(0xF5, 0xFF, 0xFA, 0xFF);
	public static final Color MISTY_ROSE = new Color(0xFF, 0xE4, 0xE1, 0xFF);
	public static final Color MOCCASIN = new Color(0xFF, 0xE4, 0xB5, 0xFF);
	public static final Color NAVAJO_WHITE = new Color(0xFF, 0xDE, 0xAD, 0xFF);
	public static final Color NAVY = new Color(0x00, 0x00, 0x80, 0xFF);
	public static final Color OLDLACE = new Color(0xFD, 0xF5, 0xE6, 0xFF);
	public static final Color OLIVE = new Color(0x80, 0x80, 0x00, 0xFF);
	public static final Color OLIVE_DRAB = new Color(0x6B, 0x8E, 0x23, 0xFF);
	public static final Color ORANGE = new Color(0xFF, 0xA5, 0x00, 0xFF);
	public static final Color ORANGE_RED = new Color(0xFF, 0x45, 0x00, 0xFF);
	public static final Color ORCHID = new Color(0xDA, 0x70, 0xD6, 0xFF);
	public static final Color PALE_GOLDEN_ROD = new Color(0xEE, 0xE8, 0xAA, 0xFF);
	public static final Color PALE_GREEN = new Color(0x98, 0xFB, 0x98, 0xFF);
	public static final Color PALE_TURQUOISE = new Color(0xAF, 0xEE, 0xEE, 0xFF);
	public static final Color PALE_VIOLET_RED = new Color(0xD8, 0x70, 0x93, 0xFF);
	public static final Color PAPAYA_WHIP = new Color(0xFF, 0xEF, 0xD5, 0xFF);
	public static final Color PEACH_PUFF = new Color(0xFF, 0xDA, 0xB9, 0xFF);
	public static final Color PERU = new Color(0xCD, 0x85, 0x3F, 0xFF);
	public static final Color PINK = new Color(0xFF, 0xC0, 0xCB, 0xFF);
	public static final Color PLUM = new Color(0xDD, 0xA0, 0xDD, 0xFF);
	public static final Color POWDER_BLUE = new Color(0xB0, 0xE0, 0xE6, 0xFF);
	public static final Color PURPLE = new Color(0x80, 0x00, 0x80, 0xFF);
	public static final Color RED = new Color(0xFF, 0x00, 0x00, 0xFF);
	public static final Color ROSY_BROWN = new Color(0xBC, 0x8F, 0x8F, 0xFF);
	public static final Color ROYAL_BLUE = new Color(0x41, 0x69, 0xE1, 0xFF);
	public static final Color SADDLE_BROWN = new Color(0x8B, 0x45, 0x13, 0xFF);
	public static final Color SALMON = new Color(0xFA, 0x80, 0x72, 0xFF);
	public static final Color SANDY_BROWN = new Color(0xF4, 0xA4, 0x60, 0xFF);
	public static final Color SEA_GREEN = new Color(0x2E, 0x8B, 0x57, 0xFF);
	public static final Color SEA_SHELL = new Color(0xFF, 0xF5, 0xEE, 0xFF);
	public static final Color SIENNA = new Color(0xA0, 0x52, 0x2D, 0xFF);
	public static final Color SILVER = new Color(0xC0, 0xC0, 0xC0, 0xFF);
	public static final Color SKY_BLUE = new Color(0x87, 0xCE, 0xEB, 0xFF);
	public static final Color SLATE_BLUE = new Color(0x6A, 0x5A, 0xCD, 0xFF);
	public static final Color SLATE_GRAY = new Color(0x70, 0x80, 0x90, 0xFF);
	public static final Color SLATE_GREY = new Color(0x70, 0x80, 0x90, 0xFF);
	public static final Color SNOW = new Color(0xFF, 0xFA, 0xFA, 0xFF);
	public static final Color SPRING_GREEN = new Color(0x00, 0xFF, 0x7F, 0xFF);
	public static final Color STEEL_BLUE = new Color(0x46, 0x82, 0xB4, 0xFF);
	public static final Color TAN = new Color(0xD2, 0xB4, 0x8C, 0xFF);
	public static final Color TEAL = new Color(0x00, 0x80, 0x80, 0xFF);
	public static final Color THISTLE = new Color(0xD8, 0xBF, 0xD8, 0xFF);
	public static final Color TOMATO = new Color(0xFF, 0x63, 0x47, 0xFF);
	public static final Color TURQUOISE = new Color(0x40, 0xE0, 0xD0, 0xFF);
	public static final Color VIOLET = new Color(0xEE, 0x82, 0xEE, 0xFF);
	public static final Color WHEAT = new Color(0xF5, 0xDE, 0xB3, 0xFF);
	public static final Color WHITE = new Color(0xFF, 0xFF, 0xFF, 0xFF);
	public static final Color WHITE_SMOKE = new Color(0xF5, 0xF5, 0xF5, 0xFF);
	public static final Color YELLOW = new Color(0xFF, 0xFF, 0x00, 0xFF);
	public static final Color YELLOW_GREEN = new Color(0x9A, 0xCD, 0x32, 0xFF);
	
	private static final Map<String, Color> NAMED_COLORS = Map.<String, Color>ofEntries(
			//@formatter:off
    	Map.entry("none",				NONE),
    	Map.entry("aliceblue",			ALICE_BLUE),
    	Map.entry("antiquewhite",		ANTIQUE_WHITE),
    	Map.entry("aqua",				AQUA),
    	Map.entry("aquamarine",			AQUA_MARINE),
    	Map.entry("azure",				AZURE),
    	Map.entry("beige",				BEIGE),
    	Map.entry("bisque",				BISQUE),
    	Map.entry("black",				BLACK),
    	Map.entry("blanchedalmond",		BLANCHED_ALMOND),
    	Map.entry("blue",				BLUE),
    	Map.entry("blueviolet",			BLUE_VIOLET),
    	Map.entry("brown",				BROWN),
    	Map.entry("burlywood",			BURLY_WOOD),
    	Map.entry("cadetblue",			CADET_BLUE),
    	Map.entry("chartreuse",			CHARTREUSE),
    	Map.entry("chocolate",			CHOCOLATE),
    	Map.entry("coral",				CORAL),
    	Map.entry("cornflowerblue",		CORNFLOWER_BLUE),
    	Map.entry("cornsilk",			CORNSILK),
    	Map.entry("crimson",				CRIMSON),
    	Map.entry("cyan",				CYAN),
    	Map.entry("darkblue",			DARK_BLUE),
    	Map.entry("darkcyan",			DARK_CYAN),
    	Map.entry("darkgoldenrod",		DARK_GOLDENROD),
    	Map.entry("darkgray",			DARK_GRAY),
    	Map.entry("darkgrey",			DARK_GREY),
    	Map.entry("darkgreen",			DARK_GREEN),
    	Map.entry("darkkhaki",			DARK_KHAKI),
    	Map.entry("darkmagenta",			DARK_MAGENTA),
    	Map.entry("darkolivegreen",		DARK_OLIVEGREEN),
    	Map.entry("darkorange",			DARK_ORANGE),
    	Map.entry("darkorchid",			DARK_ORCHID),
    	Map.entry("darkred",				DARK_RED),
    	Map.entry("darksalmon",			DARK_SALMON),
    	Map.entry("darkseagreen",		DARK_SEAGREEN),
    	Map.entry("darkslateblue",		DARK_SLATE_BLUE),
    	Map.entry("darkslategray",		DARK_SLATE_GRAY),
    	Map.entry("darkslategrey",		DARK_SLATE_GREY),
    	Map.entry("darkturquoise",		DARK_TURQUOISE),
    	Map.entry("darkviolet",			DARK_VIOLET),
    	Map.entry("deeppink",			DEEP_PINK),
    	Map.entry("deepskyblue",			DEEP_SKY_BLUE),
    	Map.entry("dimgray",				DIM_GRAY),
    	Map.entry("dimgrey",				DIM_GREY),
    	Map.entry("dodgerblue",			DODGER_BLUE),
    	Map.entry("firebrick",			FIRE_BRICK),
    	Map.entry("floralwhite",			FLORAL_WHITE),
    	Map.entry("forestgreen",			FOREST_GREEN),
    	Map.entry("fuchsia",				FUCHSIA),
    	Map.entry("gainsboro",			GAINSBORO),
    	Map.entry("ghostwhite",			GHOST_WHITE),
    	Map.entry("gold",				GOLD),
    	Map.entry("goldenrod",			GOLDEN_ROD),
    	Map.entry("gray",				GRAY),
    	Map.entry("grey",				GREY),
    	Map.entry("green",				GREEN),
    	Map.entry("greenyellow",			GREEN_YELLOW),
    	Map.entry("honeydew",			HONEY_DEW),
    	Map.entry("hotpink",				HOT_PINK),
    	Map.entry("indianred",			INDIAN_RED),
    	Map.entry("indigo",				INDIGO),
    	Map.entry("ivory",				IVORY),
    	Map.entry("khaki",				KHAKI),
    	Map.entry("lavender",			LAVENDER),
    	Map.entry("lavenderblush",		LAVENDER_BLUSH),
    	Map.entry("lawngreen",			LAWN_GREEN),
    	Map.entry("lemonchiffon",		LEMON_CHIFFON),
    	Map.entry("lightblue",			LIGHT_BLUE),
    	Map.entry("lightcoral",			LIGHT_CORAL),
    	Map.entry("lightcyan",			LIGHT_CYAN),
    	Map.entry("lightgoldenrodyellow",	LIGHT_GOLDEN_ROD_YELLOW),
    	Map.entry("lightgray",			LIGHT_GRAY),
    	Map.entry("lightgrey",			LIGHT_GREY),
    	Map.entry("lightgreen",			LIGHT_GREEN),
    	Map.entry("lightpink",			LIGHT_PINK),
    	Map.entry("lightsalmon",			LIGHT_SALMON),
    	Map.entry("lightseagreen",		LIGHT_SEA_GREEN),
    	Map.entry("lightskyblue",		LIGHT_SKY_BLUE),
    	Map.entry("lightslategray",		LIGHT_SLATE_GRAY),
    	Map.entry("lightslategrey",		LIGHT_SLATE_GREY),
    	Map.entry("lightsteelblue",		LIGHT_STEEL_BLUE),
    	Map.entry("lightyellow",			LIGHT_YELLOW),
    	Map.entry("lime",				LIME),
    	Map.entry("limegreen",			LIME_GREEN),
    	Map.entry("linen",				LINEN),
    	Map.entry("magenta",				MAGENTA),
    	Map.entry("maroon",				MAROON),
    	Map.entry("mediumaquamarine",	MEDIUM_AQUA_MARINE),
    	Map.entry("mediumblue",			MEDIUM_BLUE),
    	Map.entry("mediumorchid",		MEDIUM_ORCHID),
    	Map.entry("mediumpurple",		MEDIUM_PURPLE),
    	Map.entry("mediumseagreen",		MEDIUM_SEA_GREEN),
    	Map.entry("mediumslateblue",		MEDIUM_SLATE_BLUE),
    	Map.entry("mediumspringgreen",	MEDIUM_SPRING_GREEN),
    	Map.entry("mediumturquoise",		MEDIUM_TURQUOISE),
    	Map.entry("mediumvioletred",		MEDIUM_VIOLET_RED),
    	Map.entry("midnightblue",		MIDNIGHT_BLUE),
    	Map.entry("mintcream",			MINT_CREAM),
    	Map.entry("mistyrose",			MISTY_ROSE),
    	Map.entry("moccasin",			MOCCASIN),
    	Map.entry("navajowhite",			NAVAJO_WHITE),
    	Map.entry("navy",				NAVY),
    	Map.entry("oldlace",				OLDLACE),
    	Map.entry("olive",				OLIVE),
    	Map.entry("olivedrab",			OLIVE_DRAB),
    	Map.entry("orange",				ORANGE),
    	Map.entry("orangered",			ORANGE_RED),
    	Map.entry("orchid",				ORCHID),
    	Map.entry("palegoldenrod",		PALE_GOLDEN_ROD),
    	Map.entry("palegreen",			PALE_GREEN),
    	Map.entry("paleturquoise",		PALE_TURQUOISE),
    	Map.entry("palevioletred",		PALE_VIOLET_RED),
    	Map.entry("papayawhip",			PAPAYA_WHIP),
    	Map.entry("peachpuff",			PEACH_PUFF),
    	Map.entry("peru",				PERU),
    	Map.entry("pink",				PINK),
    	Map.entry("plum",				PLUM),
    	Map.entry("powderblue",			POWDER_BLUE),
    	Map.entry("purple",				PURPLE),
    	Map.entry("red",					RED),
    	Map.entry("rosybrown",			ROSY_BROWN),
    	Map.entry("royalblue",			ROYAL_BLUE),
    	Map.entry("saddlebrown",			SADDLE_BROWN),
    	Map.entry("salmon",				SALMON),
    	Map.entry("sandybrown",			SANDY_BROWN),
    	Map.entry("seagreen",			SEA_GREEN),
    	Map.entry("seashell",			SEA_SHELL),
    	Map.entry("sienna",				SIENNA),
    	Map.entry("silver",				SILVER),
    	Map.entry("skyblue",				SKY_BLUE),
    	Map.entry("slateblue",			SLATE_BLUE),
    	Map.entry("slategray",			SLATE_GRAY),
    	Map.entry("slategrey",			SLATE_GREY),
    	Map.entry("snow",				SNOW),
    	Map.entry("springgreen",			SPRING_GREEN),
    	Map.entry("steelblue",			STEEL_BLUE),
    	Map.entry("tan",					TAN),
    	Map.entry("teal",				TEAL),
    	Map.entry("thistle",				THISTLE),
    	Map.entry("tomato",				TOMATO),
    	Map.entry("turquoise",			TURQUOISE),
    	Map.entry("violet",				VIOLET),
    	Map.entry("wheat",				WHEAT),
    	Map.entry("white",				WHITE),
    	Map.entry("whitesmoke",			WHITE_SMOKE),
    	Map.entry("yellow",				YELLOW),
    	Map.entry("yellowgreen",			YELLOW_GREEN)
		//@formatter:on
	);
	
	public static Color get(final String name) {
		return NAMED_COLORS.get(name.toLowerCase());
	}
	
	public static Color valueOf(final String colorBase) throws Exception {
		// remove all white space...
		String color = colorBase.replace(" \r\n\t\\(\\)", "");
		if (color.isEmpty()) {
			return new Color(0, 0, 0, 1.0f);
		}
		final Color named = get(colorBase);
		if (named != null) {
			return named;
		} else if (color.charAt(0) == '#') {
			// MODEL: #RGB
			//        #RGBA
			//        #RRGGBB
			//        #RRGGBBAA
			switch (color.length()) {
				case 4 -> {
					final float r = Integer.parseInt(color.substring(1, 2), 16) * 255.0f * 16.0f;
					final float g = Integer.parseInt(color.substring(2, 3), 16) * 255.0f * 16.0f;
					final float b = Integer.parseInt(color.substring(3, 4), 16) * 255.0f * 16.0f;
					return new Color(r, g, b);
				}
				case 5 -> {
					final float r = Integer.parseInt(color.substring(1, 2), 16) * 255.0f * 16.0f;
					final float g = Integer.parseInt(color.substring(2, 3), 16) * 255.0f * 16.0f;
					final float b = Integer.parseInt(color.substring(3, 4), 16) * 255.0f * 16.0f;
					final float a = Integer.parseInt(color.substring(4, 5), 16) * 255.0f * 16.0f;
					return new Color(r, g, b, a);
				}
				case 7 -> {
					final float r = Integer.parseInt(color.substring(1, 3), 16) * 255.0f;
					final float g = Integer.parseInt(color.substring(3, 5), 16) * 255.0f;
					final float b = Integer.parseInt(color.substring(5, 7), 16) * 255.0f;
					return new Color(r, g, b);
				}
				case 9 -> {
					final float r = Integer.parseInt(color.substring(1, 3), 16) * 255.0f;
					final float g = Integer.parseInt(color.substring(3, 5), 16) * 255.0f;
					final float b = Integer.parseInt(color.substring(5, 7), 16) * 255.0f;
					final float a = Integer.parseInt(color.substring(7, 9), 16) * 255.0f;
					return new Color(r, g, b, a);
				}
				default -> throw new Exception("Can not parse color ... '" + colorBase + "'");
			}
		} else {
			// Model: r.r,g.g,b.b
			//        r.r,g.g,b.b,a.a
			//       (r.r,g.g,b.b)
			//       (r.r,g.g,b.b,a.a)
			//       rgb(r.r,g.g,b.b)
			//       rgba(r.r,g.g,b.b,a.a)
			//       argb(a.a,r.r,g.g,b.b)
			if (color.startsWith("argb")) {
				color = color.replace("argb", "");
				final String[] vals = color.split(",");
				if (vals.length == 4) {
					final float a = FMath.avg(0.0f, Float.parseFloat(vals[0]), 1.0f);
					final float r = FMath.avg(0.0f, Float.parseFloat(vals[1]), 1.0f);
					final float g = FMath.avg(0.0f, Float.parseFloat(vals[2]), 1.0f);
					final float b = FMath.avg(0.0f, Float.parseFloat(vals[3]), 1.0f);
					return new Color(r, g, b, a);
				} else {
					throw new Exception("Can not parse color ... '" + colorBase + "'");
				}
			}
			color = color.replace("rgb", "");
			color = color.replace("rgba", "");
			final String[] vals = color.split(",");
			if (vals.length == 3) {
				final float r = FMath.avg(0.0f, Float.parseFloat(vals[0]), 1.0f);
				final float g = FMath.avg(0.0f, Float.parseFloat(vals[1]), 1.0f);
				final float b = FMath.avg(0.0f, Float.parseFloat(vals[2]), 1.0f);
				return new Color(r, g, b);
			} else if (vals.length == 4) {
				final float r = FMath.avg(0.0f, Float.parseFloat(vals[0]), 1.0f);
				final float g = FMath.avg(0.0f, Float.parseFloat(vals[1]), 1.0f);
				final float b = FMath.avg(0.0f, Float.parseFloat(vals[2]), 1.0f);
				final float a = FMath.avg(0.0f, Float.parseFloat(vals[3]), 1.0f);
				return new Color(r, g, b, a);
			} else {
				throw new Exception("Can not parse color ... '" + colorBase + "'");
			}
		}
	}
	
	public Color(final float r, final float g, final float b) {
		this(r, g, b, 1.0f);
	}
	
	public Color(final float r, final float g, final float b, final float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	@Override
	public String toString() {
		return "rgba(" + this.r + ", " + this.g + ", " + this.b + ", " + this.a + ")";
	}
	
}
