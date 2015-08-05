package ca.grm.rot.libs;

import java.util.Random;

public class RotNamer {
	public static final String[] firstNameSyllables = {"fu", "ck", "b", "oi", "sh", "it", "br", "ea", "th", "a", "ss", "ter"};
	public static final String[] lastNameSyllables = {"wh", "ore", "mou", "th", "fi", "sh", "ti", "tty", "ni", "pp", "le", "di", "ck"};
	private static Random random = new Random();
	
	public static String getFirstName(int syllables)
	{
		String name = "";
		for (int i = syllables; i > -1; i--)
		{
			name += firstNameSyllables[random.nextInt(firstNameSyllables.length)];
		}
		return name;
	}
	
	public static String getLastName(int syllables)
	{
		String name = "";
		for (int i = syllables; i > -1; i--)
		{
			name += lastNameSyllables[random.nextInt(lastNameSyllables.length)];
		}
		return name;
	}
}
