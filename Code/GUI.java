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

    private final int frameConstant;
    private final int startingX;
    private final int startingY;
    private final int widthConstant;
    private final int heightConstant;
    private final int columnConstant;

    private final File accountDataFile;

    //Creates the frames for later use.
    public GUI() {
        loginVisible = false;
        createAccountVisible = false;
        frameConstant = 300;
        startingX = 50;
        startingY = 0;
        widthConstant = 100;
        heightConstant = 25;
        columnConstant = 16;
        accountDataFile = new File("AccountData.txt");
        accountLogin();
        createAccount();
        loginVisible = toggleFrame(accountLoginFrame, loginVisible);
    }

    //Sets the dimensions of the inputted frame. Need to expand later to change frame design.
    private void setFrame(JFrame frame, int width, int height) {
        frame.setSize(width, height);
        frame.setLayout(null);
    }

    //Toggles inputted frame on and off and updates the inputted frame's visible variable.
    private boolean toggleFrame(JFrame frame, boolean visible) {
        visible = !visible;
        frame.setVisible(visible);
        return visible;
    }

    //Creates the username and password components based on inputted values.
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

    //Adds the username and password components to inputted frame.
    private void addUsernameAndPassword(JFrame frame) {
        frame.add(usernameTextLabel);
        frame.add(usernameTextField);
        frame.add(passwordTextLabel);
        frame.add(passwordTextField);
    }

    //Creates the login button based on inputted values. Action listener still needs to be done.
    private void generateLoginButton(int initialX, int initialY, int width, int height) {
        loginButton = new JButton("Login");
        loginButton.setBounds(initialX, initialY, width, height);
        loginButton.addActionListener(event -> {
            //Add toggle.
        });
    }

    //Adds the login button to inputted frame.
    private void addLoginButton(JFrame frame) {
        frame.add(loginButton);
    }

    //Creates the create account label based on inputted values.
    private void generateCreateAccountLabel(int initialX, int initialY, int width, int height) {
        createAccountLabel = new JLabel("New User?");
        createAccountLabel.setBounds(initialX, initialY, width, height);
        createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    //Adds the create account label to inputted frame.
    private void addCreateAccountLabel(JFrame frame) {
        frame.add(createAccountLabel);
    }

    //Creates the create account button, which changes purpose based on the current frame.
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

    //Adds the create account button to inputted frame.
    private void addCreateAccountButton(JFrame frame) {
        frame.add(createAccountButton);
    }

    //Creates the email and discord components based on inputted values.
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

    //Adds the email and discord components to inputted frame.
    private void addEmailAndDiscord(JFrame frame) {
        frame.add(emailTextLabel);
        frame.add(emailTextField);
        frame.add(discordTextLabel);
        frame.add(discordTextField);
    }

    //Creates the go back button based on inputted values.
    private void generateGoBackButton(int initialX, int initialY, int width, int height) {
        goBackButton = new JButton("Go Back");
        goBackButton.setBounds(initialX, initialY, width, height);
        goBackButton.addActionListener(event -> {
            loginVisible = toggleFrame(accountLoginFrame, loginVisible);
            createAccountVisible = toggleFrame(createAccountFrame, createAccountVisible);
            clearTextFields();
        });
    }

    //Adds the go back button to inputted frame.
    private void addGoBackButton(JFrame frame) {
        frame.add(goBackButton);
    }

    //Clears the text fields for both frames. Used for changing frames.
    private void clearTextFields() {
        usernameTextField.setText("");
        passwordTextField.setText("");
        emailTextField.setText("");
        discordTextField.setText("");
    }

    //Returns true if the account inputs are valid. Checks input lengths and for certain values.
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

    //Loads all the accounts in a list from the file to keep data persistent. Warns about unchecked casting.
    private ArrayList<Account> loadAccounts() {
        ArrayList<Account> loadedAccounts = new ArrayList<>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(accountDataFile));
            loadedAccounts = (ArrayList<Account>)objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (IOException | ClassNotFoundException exception) {
            //exception.printStackTrace();
        }
        return loadedAccounts;
    }

    //If able to add the inputted account it will return true, otherwise will return false.
    private boolean canAddAccount(Account newAccount) {
        ArrayList<Account> loadedAccounts = loadAccounts();
        for (Account loadedAccount : loadedAccounts) {
            if (!newAccount.compareAccounts(loadedAccount)) {
                return false;
            }
        }
        return true;
    }

    //Adds the account to a list and then serializes the list to the account data file.
    private void addAccount(Account newAccount) {
        ArrayList<Account> loadedAccounts = loadAccounts();
        loadedAccounts.add(newAccount);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(accountDataFile));
            objectOutputStream.writeObject(loadedAccounts);
            objectOutputStream.close();
        }
        catch (IOException exception) {
            //exception.printStackTrace();
        }
    }

    //Frame for account login. Contains link to account creation frame.
    private void accountLogin() {
        accountLoginFrame = new JFrame("Account Login");
        setFrame(accountLoginFrame, frameConstant, frameConstant);
        //Changed all values to variables to move the UI easier.
        generateUsernameAndPassword(startingX, startingY, (widthConstant + 100), heightConstant, columnConstant);
        generateLoginButton((startingX + 50), (startingY + 110), widthConstant, heightConstant);
        generateCreateAccountLabel(startingX, (startingY + 150), (widthConstant + 100), heightConstant);
        generateCreateAccountButton((startingX + 25), (startingY + 175), (widthConstant + 50), heightConstant);
        //Adds all JFrame components to the frame.
        addUsernameAndPassword(accountLoginFrame);
        addLoginButton(accountLoginFrame);
        addCreateAccountLabel(accountLoginFrame);
        addCreateAccountButton(accountLoginFrame);
    }

    //Frame for account creation. Contains link to go back to login. If account created it auto goes back.
    private void createAccount() {
        createAccountFrame = new JFrame("Create Account");
        setFrame(createAccountFrame, frameConstant, (frameConstant + 25));
        //Changed all values to variables to move the UI easier.
        generateUsernameAndPassword(startingX, startingY, (widthConstant + 100), heightConstant, columnConstant);
        generateEmailAndDiscord(startingX, (startingY + 100), (widthConstant + 100), heightConstant, columnConstant);
        generateCreateAccountButton((startingX + 25), (startingY + 210), (widthConstant + 50), heightConstant);
        generateGoBackButton((startingX + 25), (startingY + 245), (widthConstant + 50), heightConstant);
        //Adds all JFrame components to the frame.
        addUsernameAndPassword(createAccountFrame);
        addEmailAndDiscord(createAccountFrame);
        addCreateAccountButton(createAccountFrame);
        addGoBackButton(createAccountFrame);
    }
}
