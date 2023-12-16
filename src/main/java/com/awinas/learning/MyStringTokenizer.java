package com.awinas.learning;

import java.util.StringTokenizer;

public class MyStringTokenizer {

	public static void main(String[] args) {

		String str = "SBAI179 - 3340288 - SELLA INDIA SOFTWARE SERVICES PRIVATE LIMITED - 1483455 - Banca Sella Holding S.p.A.;SIBZ179 - 1367924 - Banca Patrimoni Sella & C. S.p.A. - 1367924 - Banca Patrimoni Sella & C. S.p.A.;BSZ179 - 1 - Banca Sella S.p.A. - 1 - Banca Sella S.p.A.;SHBZ179 - 1483455 - Banca Sella Holding S.p.A. - 1483455 - Banca Sella Holding S.p.A.;IDEZ179 - 10824268 - Banca Aidexa S.p.A. - 10824268 - Banca Aidexa S.p.A.;HYPZ179 - 8992677 - Hype S.p.A. - 8992677 - Hype S.p.A.;ILBZ179 - 8291315 - illimity S.p.A. - 8291315 - illimity S.p.A.;";
		StringTokenizer userInformations = new StringTokenizer(str, ";");
		while (userInformations.hasMoreTokens()) {
			String userInformationsForBank = userInformations.nextToken();

			if (!userInformationsForBank.startsWith("GBS05349")) {
				String[] informations = userInformationsForBank.split("-");
				String code = informations[0] != null ? informations[0].trim() : null;
				System.out.println("informations[1]" + informations[1]);
				System.out.println("informations[2]" + informations[2]);
				// Society society = new Society(informations[1]!=null ?
				// Long.valueOf(informations[1].trim()) : -1, informations[2]!=null ?
				// informations[2].trim() : null);
				// OperativeUser operativeUser = new OperativeUser(code, society);
				// logger.debug("<getJoinedUsers>", operativeUser);
				// operativeUsers.add(operativeUser);
			}
		}

	}

}
