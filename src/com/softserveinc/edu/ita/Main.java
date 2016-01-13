package com.softserveinc.edu.ita;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUser;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserResourceExtractor;

public class Main {
	public static void main(String[] args) {

		try {
			
			EndUserSign endUserSign = new EndUserSign();
			System.out.println(new EndUser().IsInitialized());
			endUserSign.signingAndVerifyingFile("testFile");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
