package peermarket.peershop.websocket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Getter
public class Greeting {
    private String content;

    public Greeting(String content) {
        this.content = content;
    }
}
