package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chattry.R;

import java.util.ArrayList;

import Models.Message;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    ArrayList<Message> messagesList;

    //это интовый флаг для типа сообщения
    public static final int CONNECTION_MESSAGE = 2;
    public static final int DISCONNECTION_MESSAGE = 1;

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



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CONNECTION_MESSAGE) {
            return new MessageConnectionViewHolder(LayoutInflater.from(context).inflate(R.layout.connection_message_template, parent, false));
        }

        //тут возвращаем вью сообщения дисконекта
        return new MessageDisconnectionViewHolder(LayoutInflater.from(context).inflate(R.layout.disconnection_template, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (messagesList.get(position).getType() == CONNECTION_MESSAGE) {
            ((MessageConnectionViewHolder) holder).bind(position);
        }else if(messagesList.get(position).getType() == DISCONNECTION_MESSAGE){
            ((MessageDisconnectionViewHolder) holder).bind(position);
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