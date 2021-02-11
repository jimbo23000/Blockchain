import javax.swing.*;
import java.io.*;
import java.util.*;

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
            if (createAccountVisible && (canAddAccount())) {
                addAccount();
                loginVisible = toggleFrame(accountLoginFrame, loginVisible);
                createAccountVisible = toggleFrame(createAccountFrame, createAccountVisible);
                clearTextFields();
            } else if (createAccountVisible && (!canAddAccount())) {
                //Don't clear text fields.
            } else {
                loginVisible = toggleFrame(accountLoginFrame, loginVisible);
                createAccountVisible = toggleFrame(createAccountFrame, createAccountVisible);
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

    //Returns whether the input is repeating and the index of the repetition.
    private String checkForRepeating(int index, String inputText) {
        Scanner accountDataScanner;
        ArrayList<String> accountDataList;
        int counter = -1;
        try {
            accountDataScanner = new Scanner(accountDataFile);
            accountDataList = new ArrayList<>();
            while (accountDataScanner.hasNextLine()) {
                Scanner lineScanner = new Scanner(accountDataScanner.nextLine());
                String accountDataText = "";
                for (int i = -1; i < index; i++) {
                    accountDataText = lineScanner.next();
                }
                accountDataList.add(accountDataText);
            }
        } catch (FileNotFoundException exception) {
            System.out.println("FileNotFoundException");
            return true + " " + counter;
        }
        for (String text : accountDataList) {
            counter++;
            if (text.equals(inputText)) {
                return true + " " + counter;
            }
        }
        return false + " " + counter;
    }

    private boolean canAddUsername() {
        String usernameText = usernameTextField.getText();
        String[] isRepeating = checkForRepeating(0, usernameText).split("\\s+");
        if ((usernameText == null) || (usernameText.length() < 5) || (Boolean.parseBoolean(isRepeating[0]))) {
            usernameTextLabel.setText("Enter Another Username Here:");
            usernameTextField.setText("");
            return false;
        }
        return true;
    }

    private boolean canAddPassword() {
        String passwordText = passwordTextField.getText();
        String[] isRepeating = checkForRepeating(1, passwordText).split("\\s+");
        if ((passwordText == null) || (passwordText.length() < 8) || (Boolean.parseBoolean(isRepeating[0]))) {
            passwordTextLabel.setText("Enter Another Password Here:");
            passwordTextField.setText("");
            return false;
        }
        return true;
    }

    private boolean canAddEmail() {
        String emailText = emailTextField.getText();
        String[] isRepeating = checkForRepeating(2, emailText).split("\\s+");
        if ((emailText == null) || (Boolean.parseBoolean(isRepeating[0]))) {
            emailTextLabel.setText("Enter Another Email Here:");
            emailTextField.setText("");
            return false;
        }
        return true;
    }

    private boolean canAddDiscord() {
        String discordText = discordTextField.getText();
        String[] isRepeating = checkForRepeating(3, discordText).split("\\s+");
        if ((discordText == null) || (Boolean.parseBoolean(isRepeating[0]))) {
            discordTextLabel.setText("Enter Another Discord Here:");
            discordTextField.setText("");
            return false;
        }
        return true;
    }

    private boolean canAddAccount() {
        boolean canAdd = true;
        if (!canAddUsername()) {
            canAdd = false;
        }
        if (!canAddPassword()) {
            canAdd = false;
        }
        if (!canAddEmail()) {
            canAdd = false;
        }
        if (!canAddDiscord()) {
            canAdd = false;
        }
        return canAdd;
    }

    private void addAccount() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(accountDataFile, true));
            bufferedWriter.write(usernameTextField.getText() + ' ');
            bufferedWriter.write(passwordTextField.getText() + ' ');
            bufferedWriter.write(emailTextField.getText() + ' ');
            bufferedWriter.write(discordTextField.getText() + ' ');
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException exception) {
            System.out.println("IOException");
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
