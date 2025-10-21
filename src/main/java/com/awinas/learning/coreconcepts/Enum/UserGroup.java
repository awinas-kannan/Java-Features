package com.awinas.learning.coreconcepts.Enum;

public enum UserGroup {

	HDFC_CUSTOMER("HDFC", new String[] { "INTRANET", "INTERNET" }, 1) {
		{
			System.out.println("HDFC loaded");
		}

		@Override
		String getBankname() {
			return "HDFC";
		}
	},

	ICICI_CUSTOMER("ICICI_CUST", new String[] { "INTERNET" }, 2) {
		{
			System.out.println("ICICI_CUST loaded");
		}

		@Override
		String getBankname() {
			return "ICICI";
		}
	},
	ICICI_EMPLOYEE("ICICI_EMP", new String[] { "INTRANET" }, 1) {
		{
			System.out.println("ICICI_EMP loaded");
		}

		@Override
		String getBankname() {
			return "ICICI";
		}
	};

	public String name;
	private String[] channels;
	private int authSteps;

	private UserGroup(String name, String[] channels, int authSteps) {
		this.name = name;
		this.channels = channels;
		this.authSteps = authSteps;

	}

	public static UserGroup resolveGroupsByName(String name) throws IllegalArgumentException {

		// Lists group in added order
		for (UserGroup group : UserGroup.values()) {
			System.out.println(group.getName());
			if (group.getName().equals(name)) {
				return group;
			}
		}
		throw new IllegalArgumentException("Invalid Group");
	}

	public String getName() {
		return name;
	}

	public String[] getChannels() {
		return channels;
	}

	public int getAuthSteps() {
		return authSteps;
	}

	abstract String getBankname();

}
