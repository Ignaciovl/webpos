package com.onboarding.pos;

import com.onboarding.pos.cli.CliHandler;
import com.onboarding.pos.util.SystemHelper;

public final class Main {
	
	private Main() {
		
	}

	public static void main(final String[] args) {
		SystemHelper systemHelper = new SystemHelper();
		CliHandler cliHandler = new CliHandler(systemHelper);
		cliHandler.start(args);
	}
}
