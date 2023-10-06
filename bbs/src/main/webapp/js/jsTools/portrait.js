    function getPortraitTable(cols,caption,cn) {  
        var PortraitText = ["微笑","撇嘴","色","发呆","得意","流泪","害羞","闭嘴","睡","大哭","尴尬","发怒","调皮","呲牙","惊讶","难过","酷","冷汗","抓狂","吐","偷笑","可爱","白眼","傲慢","饥饿","困","惊恐","流汗","憨笑","大兵","奋斗","咒骂","疑问","嘘...","晕","折磨","衰","骷髅","敲打","再见","擦汗","抠鼻","鼓掌","糗大了","坏笑","左哼哼","右哼哼","哈欠","鄙视","委屈","快哭了","阴险","亲亲","吓","可怜"];  
        var table = document.createElement("table");  
        if (cn) {table.className = cn;}  
        if (caption) {table.createCaption().innerHTML = caption;}  
        var PortraitNum = PortraitText.length;  
        var rowsNum = Math.ceil(PortraitNum/cols);  
        var row;  
        var cell;  
        var img;  
        var index;  
        for (var i=0;i<rowsNum;i++) {  
            row = table.insertRow(i);  
            for (var j=0;j< cols;j++) {  
                index = cols*i+j;  
                if (PortraitText[index]) {  
                    cell = row.insertCell(j);  
                    img = document.createElement("img");  
                    img.title= PortraitText[index];  
                    img.alt =PortraitText[index];  
                    img.src="images/portrait/"+index+".gif";  
                    cell.appendChild(img);  
                } else {  
                    break;  
                }  
            }  
        }  
        return table;  
    }  