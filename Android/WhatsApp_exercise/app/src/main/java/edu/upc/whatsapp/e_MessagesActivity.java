package edu.upc.whatsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.upc.whatsapp.comms.RPC;
import edu.upc.whatsapp.adapter.MyAdapter_messages;
import edu.upc.whatsapp.service.PushService;
import entity.Message;

public class e_MessagesActivity extends Activity {

    _GlobalState globalState;
    ProgressDialog progressDialog;
    private ListView conversation;
    private MyAdapter_messages adapter;
    private EditText input_text;
    private Button button;
    private boolean enlarged = false, shrunk = true;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_messages);
        globalState = (_GlobalState) getApplication();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Talking with: " + globalState.user_to_talk_to.getName());
        setup_input_text();
        new fetchAllMessages_Task().execute(globalState.my_user.getId(), globalState.user_to_talk_to.getId());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(e_MessagesActivity.this, PushService.class);
                startService(intent);
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
       // timer.schedule(new fetchNewMessagesTimerTask(), 10000, 10000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    private class fetchAllMessages_Task extends AsyncTask<Integer, Void, List<Message>> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(e_MessagesActivity.this,
                    "MessagesActivity", "downloading messages...");
        }

        @Override
        protected List<Message> doInBackground(Integer... userIds) {
            return RPC.retrieveMessages(userIds[0], userIds[1]);
        }

        @Override
        protected void onPostExecute(List<Message> all_messages) {
            progressDialog.dismiss();
            if (all_messages == null) {
                toastShow("There's been an error downloading the messages");
            } else {
                toastShow(all_messages.size() + " messages downloaded");
                adapter = new MyAdapter_messages(getBaseContext(), all_messages, globalState.my_user);
                conversation = findViewById(R.id.conversation);
                conversation.setAdapter(adapter);
            }
        }
    }

    private class fetchNewMessages_Task extends AsyncTask<Integer, Void, List<Message>> {

        @Override
        protected List<Message> doInBackground(Integer... userIds) {
            return RPC.retrieveNewMessages(userIds[0], userIds[1], adapter.getLastMessage());
        }

        @Override
        protected void onPostExecute(List<Message> new_messages) {
            if (new_messages == null) {
                toastShow("There's been an error downloading new messages");
            } else {
                toastShow(new_messages.size() + " new message/s downloaded");
                adapter.addMessages(new_messages);
                conversation.setAdapter(adapter);
            }
        }
    }

    public void sendText(final View view) {
        new SendMessage_Task().execute();

        input_text.setText("");
        //to hide the soft keyboard after sending the message:
        InputMethodManager inMgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inMgr.hideSoftInputFromWindow(input_text.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private class SendMessage_Task extends AsyncTask<Message, Void, Message> {

        @Override
        protected void onPreExecute() {
            toastShow("sending message");
        }

        @Override
        protected Message doInBackground(Message... messages) {
            input_text = findViewById(R.id.input);
            Message message = new Message();
            message.setContent(input_text.getText().toString());
            message.setDate(new Date());
            message.setUserReceiver(globalState.user_to_talk_to);
            message.setUserSender(globalState.my_user);
            return RPC.postMessage(message);
        }

        @Override
        protected void onPostExecute(Message message_reply) {
            if (message_reply.getId() >= 0) {
                toastShow("message sent");

                adapter.addMessage(message_reply);
                conversation.setAdapter(adapter);

            } else {
                toastShow("There's been an error sending the message");
            }
        }
    }

    private class fetchNewMessagesTimerTask extends TimerTask {

        @Override
        public void run() {
            Integer[] users = {globalState.my_user.getId(), globalState.user_to_talk_to.getId()};

            new fetchNewMessages_Task().execute(users);
        }
    }

    private void setup_input_text() {

        input_text = (EditText) findViewById(R.id.input);
        button = (Button) findViewById(R.id.mybutton);
        button.setEnabled(false);

        //to be notified when the content of the input_text is modified:
        input_text.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void afterTextChanged(Editable arg0) {
                if (arg0.toString().equals("")) {
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }
            }
        });
        //to program the send soft key of the soft keyboard:
        input_text.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendText(null);
                    handled = true;
                }
                return handled;
            }
        });
        //to detect a change on the height of the window on the screen:
        input_text.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int screenHeight = input_text.getRootView().getHeight();
                Rect r = new Rect();
                input_text.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.bottom - r.top;
                int heightDifference = screenHeight - visibleHeight;
                if (heightDifference > 50 && !enlarged) {
                    LayoutParams layoutparams = input_text.getLayoutParams();
                    layoutparams.height = layoutparams.height * 2;
                    input_text.setLayoutParams(layoutparams);
                    enlarged = true;
                    shrunk = false;
                    conversation.post(new Runnable() {
                        @Override
                        public void run() {
                            conversation.setSelection(conversation.getCount() - 1);
                        }
                    });
                }
                if (heightDifference < 50 && !shrunk) {
                    LayoutParams layoutparams = input_text.getLayoutParams();
                    layoutparams.height = layoutparams.height / 2;
                    input_text.setLayoutParams(layoutparams);
                    shrunk = true;
                    enlarged = false;
                }
            }
        });
    }

    private void toastShow(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.setGravity(0, 0, 200);
        toast.show();
    }


}
