package org.iglooproject.autoprefixer;

import org.iglooproject.autoprefixer.internal.GraalAutoprefixerImpl;
import org.iglooproject.autoprefixer.internal.SimpleAutoprefixerImpl;

public class Autoprefixer {

	private Autoprefixer() {}

	public static IAutoprefixer simple() {
		return new SimpleAutoprefixerImpl();
	}

	public static IAutoprefixer graal() {
		return new GraalAutoprefixerImpl();
	}

}
