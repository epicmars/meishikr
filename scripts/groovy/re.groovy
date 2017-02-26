import java.util.regex.Matcher
import java.util.regex.Pattern


body = """忙完正事准备返回途中，真好到饭点了，回家做饭不是我这种懒人的做法，那就逛逛呗，没有目的地。天府广场站下了地铁，沿着梨花街走到一个服装批发市场，没想到市中心的中心也有像荷花池一样的地方，做生意的妹子相当多呢。市场关门较早吧，开始收摊了，穿过批发市场，在路边小吃买了一份炸土豆块，先填填肚子。

继续瞎逛。到了一个美食广场样的地方，串串，烧烤都有吧，就一个人吃这个没意思，正好前面有一家卖肥肠粉的，不是两家，还是挨着的，不过转弯过去这家没什么生意，说明不太好吃吧，一般人气旺的地方不会错。ok，就这家了，进去看了他们菜单，便问老板娘，“你们这里招牌是什么，推荐个呗”，老板娘：“我们肥肠粉好吃，再点个锅盔就更巴适了”。

"""

String toHtml(String content){
    if(null == content)
        return null;
    StringBuilder html = new StringBuilder(content);
    Pattern pattern = Pattern.compile('([^-~]+?\\s*\\n)');
    Matcher matcher = pattern.matcher(content);
    for (int i = 0; matcher.find(); i += 7){
        String paragraph = "<p>" + matcher.group() + "</p>";
        html.replace(matcher.start() + i, matcher.end() + i, paragraph);
    }
    return html.toString();
}

html = toHtml(body)

print(html)