import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class GUI {
    static JFrame accountLoginFrame;
    static JFrame createAccountFrame;

    static JTextField usernameTextField;
    static JTextField passwordTextField;
    static JTextField emailTextField;
    static JTextField discordTextField;

    static JLabel usernameTextLabel;
    static JLabel passwordTextLabel;
    static JLabel createAccountLabel;
    static JLabel emailTextLabel;
    static JLabel discordTextLabel;

    static JButton loginButton;
    static JButton createAccountButton;
    static JButton goBackButton;

    private boolean loginVisible;
    private boolean createAccountVisible;

    private final int heightConstant;
    private final int columnConstant;

    private final File accountDataFile;

    public GUI() {
        loginVisible = false;
        createAccountVisible = false;
        heightConstant = 25;
        columnConstant = 16;
        accountDataFile = new File("AccountData.txt");
        accountLogin();
        createAccount();
        loginVisible = toggleFrame(accountLoginFrame, loginVisible);
    }

    private void setFrame(JFrame frame, int width, int height) {
        frame.setSize(width, height);
        frame.setLayout(null);
    }

    private boolean toggleFrame(JFrame frame, boolean visible) {
        visible = !visible;
        frame.setVisible(visible);
        return visible;
    }

    private void generateUsernameAndPassword(int initialX, int initialY, int width, int height, int numColumns) {
        int counter = 0;
        int currentY = initialY;
        usernameTextLabel = new JLabel("Enter Username Here:");
        usernameTextLabel.setBounds(initialX, currentY, width, 25);
        usernameTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        counter++;
        currentY = (initialY + (height * counter));
        usernameTextField = new JTextField(numColumns);
        usernameTextField.setBounds(initialX, currentY, width, height);
        counter++;
        currentY = (initialY + (height * counter));
        passwordTextLabel = new JLabel("Enter Password Here:");
        passwordTextLabel.setBounds(initialX, currentY, width, height);
        passwordTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        counter++;
        currentY = (initialY + (height * counter));
        passwordTextField = new JTextField(numColumns);
        passwordTextField.setBounds(initialX, currentY, width, height);
    }

    private void addUsernameAndPassword(JFrame frame) {
        frame.add(usernameTextLabel);
        frame.add(usernameTextField);
        frame.add(passwordTextLabel);
        frame.add(passwordTextField);
    }

    private void generateLoginButton(int initialX, int initialY, int width, int height) {
        loginButton = new JButton("Login");
        loginButton.setBounds(initialX, initialY, width, height);
        loginButton.addActionListener(event -> {
            //Add toggle.
        });
    }

    private void addLoginButton(JFrame frame) {
        frame.add(loginButton);
    }

    private void generateCreateAccountLabel(int initialX, int initialY, int width, int height) {
        createAccountLabel = new JLabel("New User?");
        createAccountLabel.setBounds(initialX, initialY, width, height);
        createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void addCreateAccountLabel(JFrame frame) {
        frame.add(createAccountLabel);
    }

    private void generateCreateAccountButton(int initialX, int initialY, int width, int height) {
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(initialX, initialY, width, height);
        createAccountButton.addActionListener(event -> {
            if (!createAccountVisible) {
                loginVisible = toggleFrame(accountLoginFrame, loginVisible);
                createAccountVisible = toggleFrame(createAccountFrame, createAccountVisible);
                clearTextFields();
            } else if (canParseInputs()) {
                Account newAccount = new Account(usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), discordTextField.getText());
                if (canAddAccount(newAccount)) {
                    addAccount(newAccount);
                    loginVisible = toggleFrame(accountLoginFrame, loginVisible);
                    createAccountVisible = toggleFrame(createAccountFrame, createAccountVisible);
                    clearTextFields();
                }
            }
        });
    }

    private void addCreateAccountButton(JFrame frame) {
        frame.add(createAccountButton);
    }

    private void generateEmailAndDiscord(int initialX, int initialY, int width, int height, int numColumns) {
        int counter = 0;
        int currentY = initialY;
        emailTextLabel = new JLabel("Enter Email Here:");
        emailTextLabel.setBounds(initialX, currentY, width, 25);
        emailTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        counter++;
        currentY = (initialY + (height * counter));
        emailTextField = new JTextField(numColumns);
        emailTextField.setBounds(initialX, currentY, width, height);
        counter++;
        currentY = (initialY + (height * counter));
        discordTextLabel = new JLabel("Enter Discord Here:");
        discordTextLabel.setBounds(initialX, currentY, width, height);
        discordTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        counter++;
        currentY = (initialY + (height * counter));
        discordTextField = new JTextField(numColumns);
        discordTextField.setBounds(initialX, currentY, width, height);
    }

    private void addEmailAndDiscord(JFrame frame) {
        frame.add(emailTextLabel);
        frame.add(emailTextField);
        frame.add(discordTextLabel);
        frame.add(discordTextField);
    }

    private void generateGoBackButton(int initialX, int initialY, int width, int height) {
        goBackButton = new JButton("Go Back");
        goBackButton.setBounds(initialX, initialY, width, height);
        goBackButton.addActionListener(event -> {
            loginVisible = toggleFrame(accountLoginFrame, loginVisible);
            createAccountVisible = toggleFrame(createAccountFrame, createAccountVisible);
            clearTextFields();
        });
    }

    private void addGoBackButton(JFrame frame) {
        frame.add(goBackButton);
    }

    private void clearTextFields() {
        usernameTextField.setText("");
        passwordTextField.setText("");
        emailTextField.setText("");
        discordTextField.setText("");
    }

    private boolean canParseInputs() {
        boolean canParse = true;
        if (usernameTextField.getText().length() < 5) {
            usernameTextLabel.setText("Enter Longer Username:");
            usernameTextField.setText("");
            canParse = false;
        }
        if (passwordTextField.getText().length() < 8) {
            passwordTextLabel.setText("Enter Longer Password:");
            passwordTextField.setText("");
            canParse = false;
        }
        if (!emailTextField.getText().contains("@")) {
            emailTextLabel.setText("Enter Valid Email:");
            emailTextField.setText("");
            canParse = false;
        }
        if (!discordTextField.getText().contains("#")) {
            discordTextLabel.setText("Enter Valid Discord:");
            discordTextField.setText("");
            canParse = false;
        }
        return canParse;
    }

    //Loads all the accounts from the file to keep data persistent.
    private ArrayList<Account> loadAccounts() {
        ArrayList<Account> loadedAccounts = new ArrayList<>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(accountDataFile));
            loadedAccounts = (ArrayList<Account>)objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("IOException caught.");
        }
        catch (ClassNotFoundException exception) {
            exception.printStackTrace();
            System.out.println("ClassNotFoundException caught.");
        }
        return loadedAccounts;
    }

    //If able to add the account it will return true, otherwise will return false.
    private boolean canAddAccount(Account newAccount) {
        ArrayList<Account> loadedAccounts = loadAccounts();
        for (Account loadedAccount : loadedAccounts) {
            if (!newAccount.compareAccounts(loadedAccount)) {
                return false;
            }
        }
        return true;
    }

    private void addAccount(Account newAccount) {
        ArrayList<Account> loadedAccounts = loadAccounts();
        loadedAccounts.add(newAccount);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(accountDataFile));
            objectOutputStream.writeObject(loadedAccounts);
            objectOutputStream.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("IOException caught.");
        }
    }

    private void accountLogin() {
        accountLoginFrame = new JFrame("Account Login");
        setFrame(accountLoginFrame, 300, 300);
        generateUsernameAndPassword(50, 0, 200, heightConstant, columnConstant);
        generateLoginButton(100, 110, 100, heightConstant);
        generateCreateAccountLabel(50, 150, 200, heightConstant);
        generateCreateAccountButton(75, 175, 150, heightConstant);
        addUsernameAndPassword(accountLoginFrame);
        addLoginButton(accountLoginFrame);
        addCreateAccountLabel(accountLoginFrame);
        addCreateAccountButton(accountLoginFrame);
    }

    private void createAccount() {
        createAccountFrame = new JFrame("Create Account");
        setFrame(createAccountFrame, 300, 325);
        generateUsernameAndPassword(50, 0, 200, heightConstant, columnConstant);
        generateEmailAndDiscord(50, 100, 200, heightConstant, columnConstant);
        generateCreateAccountButton(75, 210, 150, heightConstant);
        generateGoBackButton(75, 245, 150, heightConstant);
        addUsernameAndPassword(createAccountFrame);
        addEmailAndDiscord(createAccountFrame);
        addCreateAccountButton(createAccountFrame);
        addGoBackButton(createAccountFrame);
    }
}
