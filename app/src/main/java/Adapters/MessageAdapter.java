package Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattry.R;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Models.Message;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    ArrayList<Message> messagesList;

    //это интовый флаг для типа сообщения
    public static final int CONNECTION_MESSAGE = 2;
    public static final int DISCONNECTION_MESSAGE = 1;

    public static final int MY_MESSAGE = 3;
    public static final int OUTER_MESSAGE = 4;

    public MessageAdapter(Context context, ArrayList<Message> messagesList) {
        this.context = context;
        this.messagesList = messagesList;
    }

    //этот класс нужен для сета сообщения конекта в recycler-е
    private class MessageConnectionViewHolder extends RecyclerView.ViewHolder {

        TextView lblConnectionMessage;
        MessageConnectionViewHolder(final View itemView) {
            super(itemView);
            lblConnectionMessage = itemView.findViewById(R.id.lblConnectionMessage);

        }
        void bind(int position) {
            Message message = messagesList.get(position);
            lblConnectionMessage.setText(message.getContent());
        }
    }


    //этот класс нужен для сета сообщения дисконекта в recycler-е
    private class MessageDisconnectionViewHolder extends RecyclerView.ViewHolder {

        TextView lblDisconnectionMessage;
        MessageDisconnectionViewHolder(final View itemView) {
            super(itemView);
            lblDisconnectionMessage = itemView.findViewById(R.id.lblDisconnectionMessage);

        }
        void bind(int position) {
            Message message = messagesList.get(position);
            lblDisconnectionMessage.setText(message.getContent());
        }
    }

    //этот класс нужен для сета моего сообщения в recycler-е
    private class MyMessageViewHolder extends RecyclerView.ViewHolder {

        TextView lblMyMessage;
        TextView lblMyTime;
        MyMessageViewHolder(final View itemView) {
            super(itemView);
            lblMyMessage = itemView.findViewById(R.id.lblMyMessage);
            lblMyTime = itemView.findViewById(R.id.lblTimeOfMyMessage);

        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        void bind(int position) {
            Message message = messagesList.get(position);

            lblMyMessage.setText(message.getContent());
            String timeStr = message.getTimeStr();



            lblMyTime.setText(timeStr);
        }
    }
    //этот класс нужен для сета чужого сообщения в recycler-е
    private class OuterMessageViewHolder extends RecyclerView.ViewHolder {

        TextView lblOuterMessage;
        TextView lblOuterTime;
        TextView lblSenderName;
        OuterMessageViewHolder(final View itemView) {
            super(itemView);
            lblOuterMessage = itemView.findViewById(R.id.lblOuterMessage);
            lblOuterTime = itemView.findViewById(R.id.lblTimeOfOuterMessage);
            lblSenderName = itemView.findViewById(R.id.lblSenderOfOuterMessage);

        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        void bind(int position) {
            Message message = messagesList.get(position);

            lblOuterMessage.setText(message.getContent());

            String timeStr = message.getTimeStr();

            lblOuterTime.setText(timeStr);

            lblSenderName.setText(message.getSenderName());

        }
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CONNECTION_MESSAGE) {
            return new MessageConnectionViewHolder(LayoutInflater.from(context).inflate(R.layout.connection_message_template, parent, false));
        }
        if (viewType == MY_MESSAGE) {
            return new MyMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.my_message_template, parent, false));
        }
        if (viewType == OUTER_MESSAGE) {
            return new OuterMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.outer_message_temaplate, parent, false));
        }

        //тут возвращаем вью сообщения дисконекта
        return new MessageDisconnectionViewHolder(LayoutInflater.from(context).inflate(R.layout.disconnection_template, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (messagesList.get(position).getType() == CONNECTION_MESSAGE) {
            ((MessageConnectionViewHolder) holder).bind(position);
        }
        else if(messagesList.get(position).getType() == DISCONNECTION_MESSAGE){
            ((MessageDisconnectionViewHolder) holder).bind(position);
        }
        else if(messagesList.get(position).getType() == MY_MESSAGE){
            ((MyMessageViewHolder) holder).bind(position);
        }
        else if(messagesList.get(position).getType() == OUTER_MESSAGE){
            ((OuterMessageViewHolder) holder).bind(position);
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messagesList.get(position).getType();
    }
}