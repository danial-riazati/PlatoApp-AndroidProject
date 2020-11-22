package com.example.finalapproject.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapproject.Main;
import com.example.finalapproject.Mess;
import com.example.finalapproject.R;

import java.util.List;

import static com.example.finalapproject.LogIn.user;

public class MessageListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Mess> mMessageList;

    public MessageListAdapter(Context context ,List<Mess> messageList) {
        mContext = context;
        mMessageList = messageList;
    }
    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Mess message = (Mess) mMessageList.get(position);

        if (message.getSender().getUsername().equals(user.getUsername())) {
            // If the current user is the sender of the message
            return 1;
        }
        return -1;
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sent_message_item, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == -1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.received_message_item, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }


    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Mess message = (Mess) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case 1:
                ((SentMessageHolder) holder).bind(message);
                break;
            case -1:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.sent_text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.sent_text_message_time);
        }

        void bind(Mess message) {
            messageText.setText(message.getData());
            timeText.setText(message.getTime());
            // Format the stored timestamp into a readable String using method.
            //timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.rec_text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.rec_text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.rec_text_message_name);
            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(Mess message) {
            messageText.setText(message.getData());
            timeText.setText(message.getTime());
            // Format the stored timestamp into a readable String using method.
            //timeText.setText(Utils.formatDateTime(message.getCreatedAt()));

            nameText.setText(message.getSender().getUsername());

            // Insert the profile image from the URL into the ImageView.
            //Utils.displayRoundImageFromUrl(mContext, message.getSender().getPic(), profileImage);
        }
    }
}

