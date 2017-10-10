package com.sam.org.utilty;

import java.io.FileOutputStream;

import com.sam.org.constants.CsrConstants;


public class ExeCommand {

	
	/**
	 * Use to execute a command line without output file
	 * 
	 * @param cmd
	 *            - command to be executed
	 */
	public void execCmd(String cmd) {
		execCmd(CsrConstants.OPEN_SSL_FILE_PATH + cmd, "", 0);
	}
	
	public void execCmd(String cmd, String outfileName, int timeout) {
		try {
			int returnCode = execCmdReturnCode(cmd, outfileName, timeout);
			if (returnCode == 0 || returnCode == 1) {
				System.out.println("Successful execute command " + cmd);
			} else {
				// BufferedReader stdError = new BufferedReader(new
				// InputStreamReader(proc.getErrorStream()));
				// Logger.debug(stdError.readLine());
				System.out.println("Failed to execute command " + cmd);
			}
		} catch (IllegalThreadStateException i) {
			System.out.println("Command " + cmd
					+ " was executed but not exit correct. Ignore for now");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to execute a command " + cmd);
		}
	}
	
	/**
	 * Use to execute a command line
	 * 
	 * @param cmd
	 *            - Command to be executed
	 * @param outfileName
	 *            - output file
	 */
	public static int execCmdReturnCode(String cmd, String outfileName, int timeout) {
		FileOutputStream fos = null;
		Process proc = null;
		try {
			if (!outfileName.isEmpty()) {
				fos = new FileOutputStream(outfileName);
			}

			proc = Runtime.getRuntime().exec(cmd);

			StreamGobbler errorGobbler = new StreamGobbler(
					proc.getErrorStream(), "INFO");
			// any output?
			StreamGobbler outputGobbler = new StreamGobbler(
					proc.getInputStream(), "OUTPUT", fos);
			errorGobbler.start();
			outputGobbler.start();

			if (timeout > 0) {
				Thread.sleep(timeout);
				proc.destroy();
			} else {
				proc.waitFor();
			}

			if (!outfileName.isEmpty()) {
				fos.flush();
				Thread.sleep(2000);
				fos.close();
			}
		} catch (IllegalThreadStateException i) {
			System.out.println("Command " + cmd
					+ " was executed but not exit correct. Ignore for now");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to execute a command " + cmd);
		}
		return proc.exitValue();
	}
}
