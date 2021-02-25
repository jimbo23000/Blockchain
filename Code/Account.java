import java.io.*;
import java.math.BigInteger;

public class Account implements Serializable {
    private RSA accountKeys;

    private String accountName;
    private String accountPassword;
    private String accountEmail;
    private String accountDiscord;

    public Account(String _accountName, String _accountPassword, String _accountEmail, String _accountDiscord) {
        accountKeys = new RSA(1024);
        accountName = _accountName;
        accountPassword = _accountPassword;
        accountEmail = _accountEmail;
        accountDiscord = _accountDiscord;
    }

    //This method has no function at the moment.
    private RSA getAccountKeys() {
        return accountKeys;
    }

    private String getAccountName() {
        return accountName;
    }

    private String getAccountPassword() {
        return accountPassword;
    }

    private String getAccountEmail() {
        return accountEmail;
    }

    private String getAccountDiscord() {
        return accountDiscord;
    }

    public BigInteger encryptMessage(String message) {
        return accountKeys.encrypt(new BigInteger(new BigInteger(message.getBytes()).toString(2)));
    }

    public String decryptMessage(BigInteger message) {
        return new String(new BigInteger("" + accountKeys.decrypt(message), 2).toByteArray());
    }

    //Prints out the account's public and private keys.
    private void printKeys() {
        System.out.println(accountKeys);
    }

    //Checks for repeating information. If no repeating information returns true.
    public boolean compareAccounts(Account existingAccount) {
        if (!this.getAccountName().equals(existingAccount.getAccountName())) {
            if (!this.getAccountPassword().equals(existingAccount.getAccountPassword())) {
                if (!this.getAccountEmail().equals(existingAccount.getAccountEmail())) {
                    if (!this.getAccountDiscord().equals(existingAccount.getAccountDiscord())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
