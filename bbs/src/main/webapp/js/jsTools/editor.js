    function getColorTable(cn) {    
        var table = document.createElement("table");    
        table.createCaption().appendChild(document.createTextNode("颜色表"));    
        table.cellspacing=0;    
        table.cellpadding=0;    
        table.className = cn;    
        var row = table.insertRow(0);    
        for (var i=0;i<12;i++) {    
            for (var j=0;j<18;j++) {    
                var cell = row.insertCell(row.cells.length);    
                cell.innerHTML ="&nbsp;";    
                var g = fixColor((j>6)?((j%6)*3):j*3);    
                var b = fixColor((i>6)?((i%6)*3):i*3);    
                var r =parseInt(j/6)*3;    
                if (i>5) {r+=9;}    
                r= fixColor(r)    
                var color = "#"+r+g+b;    
                cell.style.backgroundColor=color;    
                cell.title = color;    
            }    
            row = table.insertRow(table.rows.length);    
        }    
        row = table.insertRow(table.rows.length);    
        return table;    
    }    
    function fixColor(c) {    
        var hexNum = [0,1,2,3,4,5,6,7,8,9,"A","B","C","D","E","F"];    
        c = parseInt(c);    
        return hexNum[c]?hexNum[c]:0;    
    }    