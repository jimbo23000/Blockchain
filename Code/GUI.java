import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class GUI {
    static JFrame loginFrame;
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

    private BufferedWriter bufferedWriter;

    private boolean loginVisible;
    private boolean createAccountVisible;

    public GUI() {
        loginVisible = false;
        createAccountVisible = false;
        accountLogin();
        loginVisible = toggleFrame(loginFrame, loginVisible);
        createAccount();
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
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

            }
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
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                loginVisible = toggleFrame(loginFrame, loginVisible);
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

    private void accountLogin() {
        int frameConstant = 300;
        loginFrame = new JFrame("Account Login");
        setFrame(loginFrame, frameConstant, frameConstant);
        generateUsernameAndPassword(50, 0, 200, 25, 16);
        generateLoginButton(100, 110, 100, 25);
        generateCreateAccountLabel(50, 150, 200, 25);
        generateCreateAccountButton(75, 175, 150, 25);
        addUsernameAndPassword(loginFrame);
        addLoginButton(loginFrame);
        addCreateAccountLabel(loginFrame);
        addCreateAccountButton(loginFrame);
    }

    private void createAccount() {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File("AccountData.dat")));
        } catch (IOException exception) {
            System.out.println("IOException");
        }
        int frameConstant = 300;
        createAccountFrame = new JFrame("Create Account");
        setFrame(createAccountFrame, frameConstant, frameConstant);
        generateUsernameAndPassword(50, 0, 200, 25, 16);
        generateEmailAndDiscord(50, 100, 200, 25, 16);
        generateCreateAccountButton(75, 210, 150, 25);
        addUsernameAndPassword(createAccountFrame);
        addEmailAndDiscord(createAccountFrame);
        addCreateAccountButton(createAccountFrame);
    }
}
