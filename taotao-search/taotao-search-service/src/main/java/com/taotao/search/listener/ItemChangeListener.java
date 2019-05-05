package com.taotao.search.listener;
import com.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
/**
 * @author chenlin
 */
public class ItemChangeListener implements MessageListener {
    @Autowired
    SearchItemService searchItemService;
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = null;
            Long itemId = null;
            //取商品id
            if (message instanceof TextMessage) {
                textMessage = (TextMessage) message;
                itemId = Long.parseLong(textMessage.getText());
            }
            //向索引库添加文是档
            searchItemService.updateItemById(itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
