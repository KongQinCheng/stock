var canvas;
var stage;
var container;
var captureContainers;
var captureIndex;

function init() {
  // create a new stage and point it at our canvas:
  canvas = document.getElementById("testCanvas");
  stage = new createjs.Stage(canvas);
  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;

  var w = canvas.width;
  var h = canvas.height;

    canvas.addEventListener('click', function(e) {
        window.location.href="/web/toHome"
    }, false)


  container = new createjs.Container();
  stage.addChild(container);

  captureContainers = [];
  captureIndex = 0;

  // create a large number of slightly complex vector shapes, and give them random positions and velocities:
  var shadow = new createjs.Shadow("#000", 2,2,5);
  
  for (var i = 0; i < 50; i++) {
    var heart = new createjs.Container();   
    var h1 = new createjs.Shape();
    h1.graphics.beginFill(createjs.Graphics.getHSL(Math.random() * 30 - 230, 100, 50 + Math.random() * 30)).p("AC3H6QgEg1hHi/IhGi0QAIBPgDAzQgDA9gWB1QgLA7guAoQgtAng+AHQhAAHg7geQhAgigohHQhJiCBrh7QBDhNBxg4IgtAOQh9Ajh6gLQhGgGgjhIQgghEALhdQALhgA0hFQA6hOBagOQCggZBhCNQAxBHARBLQgLhLAGhTQAOilBhgnQBjgnBkA3QAzAcAeAkQBZCBh2B/Qg8BAhNAmIBEgUQBIgTAVgBQCzgHAsB3QAuB7g/BhQgtBDhCAWQhDAVhgg7Qhhg7hQh5QCREgASBXQARBYgNALQgOALgQAEIgMABQggAAgDgwg")
    h1.shadow = shadow;
    heart.addChild(h1);
    heart.cache(-80,-80,160,160,0.5);
    heart.y = -100;

    container.addChild(heart);
  }


    var t1="2019/02/19 23:59:59"; //数据
    var dateBegin = new Date(t1);//转化为Date对象的形式
    var dateEnd = new Date();//当前时间数据
    var date3=dateEnd.getTime()-dateBegin.getTime()  //时间差的毫秒数
    var days=Math.floor(date3/(24*3600*1000))
    if (days%10==1) {
        days=days+"st"
    }
    if (days%10==2) {
        days=days+"nd"
    }
    if (days%10==3) {
        days=days+"rd"
    }

    var text = new createjs.Text("chen min fang\nThis is the\n "+days+" day \nI miss you.", "bold 35px 'Irish Grover'", "#fff");
  text.textAlign = "center";
  text.x = w / 2;
  text.y = h / 2 - text.getMeasuredLineHeight()-50;
  stage.addChild(text);

  for (i = 0; i < 100; i++) {
    var captureContainer = new createjs.Container();
    captureContainer.cache(0, 0, w, h);
    captureContainers.push(captureContainer);
  }

  // start the tick and point it at the window so we can do some work before updating the stage:
  createjs.Ticker.timingMode = createjs.Ticker.RAF;
  createjs.Ticker.on("tick", tick);
}

function tick(event) {
  var w = canvas.width;
  var h = canvas.height;
  var l = container.numChildren;

  captureIndex = (captureIndex + 1) % captureContainers.length;
  stage.removeChildAt(0);
  var captureContainer = captureContainers[captureIndex];
  stage.addChildAt(captureContainer, 0);
  captureContainer.addChild(container);

  // iterate through all the children and move them according to their velocity:
  for (var i = 0; i < l; i++) {
    var heart = container.getChildAt(i);
    if (heart.y < -50) {
      heart._x = Math.random() * w;
      heart.y = h * (1 + Math.random()) + 50;
      heart.perX = (1 + Math.random() * 2) * h;
      heart.offX = Math.random() * h;
      heart.ampX = heart.perX * 0.1 * (0.15 + Math.random());
      heart.velY = -Math.random() * 2 - 1;
      heart.scale = Math.random() * 0.28 + 0.28;
      heart._rotation = Math.random() * 40 - 20;
      heart.alpha = Math.random() * 0.75 + 0.05;
      heart.compositeOperation = Math.random() < 0.33 ? "lighter" : "source-over";
    }
    var int = (heart.offX + heart.y) / heart.perX * Math.PI * 2;
    heart.y += heart.velY * heart.scaleX*3.5;
    heart.x = heart._x + Math.cos(int) * heart.ampX;
    heart.rotation = heart._rotation + Math.sin(int) * 30;
  }

  captureContainer.updateCache("source-over");

  // draw the updates to stage:
  stage.update(event);
}

init();