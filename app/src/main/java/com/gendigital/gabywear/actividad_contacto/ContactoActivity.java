package com.gendigital.gabywear.actividad_contacto;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.gendigital.gabywear.R;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Gaby on 07/07/2016.
 */
public class ContactoActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private TextInputEditText inputName, inputEmail, inputMensaje;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutMensaje;
    private Button btnNext;
    private DatePicker dpBirthday;

    private int year;
    private int month;
    private int day;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        Toolbar actionBar = (Toolbar) findViewById(R.id.barraSup);
        setSupportActionBar(actionBar);

        actionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContactoActivity.this, "Regresando", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMensaje = (TextInputLayout) findViewById(R.id.input_layout_mensaje);
        inputName = (TextInputEditText) findViewById(R.id.input_name);
        inputEmail = (TextInputEditText) findViewById(R.id.input_email);
        inputMensaje = (TextInputEditText) findViewById(R.id.input_mensaje);
        btnNext = (Button) findViewById(R.id.btn_next);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputMensaje.addTextChangedListener(new MyTextWatcher(inputMensaje));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }
    /**
     * Validating form
     */
    private void submitForm() {

        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validateMensaje()) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Enviando correo...", Toast.LENGTH_SHORT).show();

        //Properties props = new Properties();
        Properties props = System.getProperties();

        props.put("mail.smtp.host", "my-mail-server");
        Session session = Session.getInstance(props, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(inputEmail.getText().toString()));

            InternetAddress[] address = {new InternetAddress("gabynufe@yahoo.com.mx")};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("Contacto desde mi app android (Pet Store)");
            msg.setSentDate(new Date());
            // If the desired charset is known, you can use
            // setText(text, charset)
            msg.setText(inputMensaje.getText().toString());

            Transport.send(msg);
        } catch (MessagingException mex) {
            System.out.println("error al enviar correo: " + mex);
        }
        /*
        Intent itSend = new Intent(android.content.Intent.ACTION_SEND);
        //Intent itSend = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ inputEmail.getText().toString()});
        itSend.putExtra(android.content.Intent.EXTRA_SUBJECT, "Contacto desde app");
        itSend.putExtra(android.content.Intent.EXTRA_TEXT, inputMensaje.getText());
        //itSend.setType("message/rfc822");
        itSend.setType("plain/text");
        startActivity(Intent.createChooser(itSend, "Escoge una aplicación :"));
*/
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            inputName.requestFocus();
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            inputEmail.requestFocus();
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMensaje() {
        if (inputMensaje.getText().toString().trim().isEmpty()) {
            inputLayoutMensaje.setError(getString(R.string.err_msg_mensaje));
            inputMensaje.requestFocus();
            return false;
        } else {
            inputLayoutMensaje.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_mensaje:
                    validateMensaje();
                    break;
            }
        }
    }
}
