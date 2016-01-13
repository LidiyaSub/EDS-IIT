package com.softserveinc.edu.ita;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUser;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserCertificateInfoEx;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserKeyMedia;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserRequestInfo;


public class EndUserSign {
	public EndUser endUser;

	public EndUserSign() throws Exception {
		initialize();
	}

	private void initialize() throws Exception {
		endUser = new EndUser();
		endUser.SetCharset("UTF-16LE");
		endUser.SetUIMode(false);
		endUser.Initialize();
	}

	private static boolean writeFile(String fileName, byte[] data) {
		java.io.File file = new java.io.File(fileName);

		try {
			java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(
					file);
			fileOutputStream.write(data);
			fileOutputStream.close();
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	// generate private key internal
	public boolean generateKeyInternal(String keyType) throws Exception {
		int keySpec;
		int uaKeySpec;
		int rsaKeySpec;
		int keyMediaTypeIndex;
		int keyMediaDeviceIndex;
		String keyMediaType;
		String keyMediaDevice;
		String keyMediasPassword;

		EndUserUtils.printMessage("Enumerating key medias...");

		keyMediaTypeIndex = 0;
		while (!(keyMediaType = endUser.EnumKeyMediaTypes(keyMediaTypeIndex))
				.equals("")) {
			System.out.println(keyMediaTypeIndex + ". " + keyMediaType);

			keyMediaDeviceIndex = 0;
			while (!(keyMediaDevice = endUser.EnumKeyMediaDevices(
					keyMediaTypeIndex, keyMediaDeviceIndex)).equals("")) {
				System.out.println("\t" + keyMediaDeviceIndex + ". "
						+ keyMediaDevice);

				keyMediaDeviceIndex++;
			}

			keyMediaTypeIndex++;
		}
		switch (keyType) {
		case "UA":
			keySpec = 1;
			break;
		case "RSA":
			keySpec = 2;
			break;
		case "UA+RSA":
			keySpec = 3;
			break;
		default:
			System.out
					.println("Enter key type (1 for UA, 2 for RSA, 3 for UA+RSA):");
			keySpec = Integer.parseInt(EndUserUtils.readMessage());
			break;
		}

		EndUserUtils.printMessage("Enter key media type index:");
		keyMediaTypeIndex = Integer.parseInt(EndUserUtils.readMessage());
		EndUserUtils.printMessage("Enter key media device index:");
		keyMediaDeviceIndex = Integer.parseInt(EndUserUtils.readMessage());
		EndUserUtils.printMessage("Enter key media password:");
		keyMediasPassword = EndUserUtils.readMessage();

		EndUserUtils.printMessage("Generating private key...");

		switch (keySpec) {
		case 1:
			uaKeySpec = EndUser.EU_KEYS_TYPE_DSTU_AND_ECDH_WITH_GOSTS;
			rsaKeySpec = EndUser.EU_KEYS_TYPE_NONE;
			break;

		case 2:
			uaKeySpec = EndUser.EU_KEYS_TYPE_NONE;
			rsaKeySpec = EndUser.EU_KEYS_TYPE_RSA_WITH_SHA;
			break;

		case 3:
			uaKeySpec = EndUser.EU_KEYS_TYPE_DSTU_AND_ECDH_WITH_GOSTS;
			rsaKeySpec = EndUser.EU_KEYS_TYPE_RSA_WITH_SHA;
			break;

		default:
			uaKeySpec = EndUser.EU_KEYS_TYPE_NONE;
			rsaKeySpec = EndUser.EU_KEYS_TYPE_NONE;
			break;
		}

		EndUserRequestInfo[] requests = endUser.GeneratePrivateKey(
				keyMediaTypeIndex, keyMediaDeviceIndex, keyMediasPassword,
				uaKeySpec, EndUser.EU_KEYS_LENGTH_DS_UA_257, false,
				EndUser.EU_KEYS_LENGTH_KEP_UA_431, "", rsaKeySpec,
				EndUser.EU_KEYS_LENGTH_DS_RSA_2048, "");

		for (EndUserRequestInfo request : requests) {
			if (writeFile(request.GetDefaultRequestFileName(),
					request.GetRequest()))
				EndUserUtils.printMessage(request.GetDefaultRequestFileName());
		}

		return true;
	}

	// read private key
	private void readPKey() throws Exception {
		int keyMediaTypeIndex;
		int keyMediaDeviceIndex;
		String keyMediaType;
		String keyMediaDevice;
		String keyMediasPassword;

		EndUserUtils.printMessage("Enumerating key medias...");

		keyMediaTypeIndex = 0;
		while (!(keyMediaType = endUser.EnumKeyMediaTypes(keyMediaTypeIndex))
				.equals("")) {
			EndUserUtils.printMessage(keyMediaTypeIndex + ". " + keyMediaType);

			keyMediaDeviceIndex = 0;
			while (!(keyMediaDevice = endUser.EnumKeyMediaDevices(
					keyMediaTypeIndex, keyMediaDeviceIndex)).equals("")) {
				EndUserUtils.printMessage("\t" + keyMediaDeviceIndex + ". "
						+ keyMediaDevice);

				keyMediaDeviceIndex++;
			}

			keyMediaTypeIndex++;
		}

		EndUserUtils.printMessage("Enter key media type index:");
		keyMediaTypeIndex = Integer.parseInt(EndUserUtils.readMessage());
		EndUserUtils.printMessage("Enter key media device index:");
		keyMediaDeviceIndex = Integer.parseInt(EndUserUtils.readMessage());
		EndUserUtils.printMessage("Enter key media password:");
		keyMediasPassword = EndUserUtils.readMessage();

		EndUserUtils.printMessage("Reading private key...");

		endUser.ReadPrivateKeySilently(new EndUserKeyMedia(keyMediaTypeIndex,
				keyMediaDeviceIndex, keyMediasPassword));
	}

	public boolean signingAndVerifyingFile(String testFile) throws Exception {
		int index = 0;
		int keyType;
		int keyUsage;
		boolean blUASign = false;
		boolean blRSASign = false;
		boolean blUseTSP;
		EndUserCertificateInfoEx certInfo;
		byte[] certificate;
		String resultFile;
		String signerInfo;

		readPKey();

		while ((certInfo = endUser.EnumOwnCertificates(index++)) != null) {
			keyType = certInfo.GetPublicKeyType();
			keyUsage = certInfo.GetKeyUsageType();

			certificate = endUser.GetCertificate(certInfo.GetIssuer(),
					certInfo.GetSerial());

			writeFile(
					"EU"
							+ ((keyType == EndUser.CERT_KEY_TYPE_DSTU4145) ? (((keyUsage & EndUser.KEY_USAGE_DIGITAL_SIGNATUR) != 0) ? ""
									: "-KEP")
									: "-RSA") + "-Signer.cer", certificate);

			if ((keyUsage & EndUser.KEY_USAGE_DIGITAL_SIGNATUR) == 0)
				continue;

			switch (keyType) {
			case EndUser.CERT_KEY_TYPE_DSTU4145:
				blUASign = true;
				break;

			case EndUser.CERT_KEY_TYPE_RSA:
				blRSASign = true;
				break;

			default:
				continue;
			}
		}

		blUseTSP = endUser.GetTSPSettings().GetGetStamps();

		if (blUASign) {
			resultFile = testFile + ".dstu.int" + (blUseTSP ? ".tsp" : "")
					+ ".p7s";

			endUser.SignFile(testFile, resultFile, false);

			signerInfo = endUser.VerifyFileWithInternalSign(resultFile,
					resultFile + ".dat", false);
			EndUserUtils.printMessage("Signer of " + resultFile + "file: "
					+ signerInfo);

		}

		if (blRSASign) {
			resultFile = testFile + ".rsa.int" + (blUseTSP ? ".tsp" : "")
					+ ".p7s";

			endUser.SignRSAFile(testFile, resultFile, false);

			signerInfo = endUser.VerifyFileWithInternalSign(resultFile,
					resultFile + ".dat", false);
			EndUserUtils.printMessage("Signer of " + resultFile + "file: "
					+ signerInfo);

		}

		return true;
	}
}
