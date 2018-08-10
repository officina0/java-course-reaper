var TurndownService = require('turndown')

var turndownService = new TurndownService()
turndownService.addRule('strikethrough', {
  filter: ['pre'],
  replacement: function (content) {
    return '\n```\n' + content + '\n```\n'
  }
})
var markdown = turndownService.turndown(takeFrom())
putTo(markdown);
