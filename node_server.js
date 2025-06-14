const express = require('express');
const fs = require('fs');
const { exec } = require('child_process');
const app = express();
const port = 5500;

app.use(express.static('.'));
app.use(express.json());

app.post('/save-input', (req, res) => {
  const { start, end } = req.body;
  const content = `${start}\n${end}`;
  fs.writeFileSync('input.txt', content);
  console.log('Saved input.txt:', content);

  exec('java -cp ".;lib/gson-2.10.1.jar" ConsoleApp', (error, stdout, stderr) => {
    if (error) {
      console.error(`Java Error: ${error.message}`);
      return res.status(500).send({ error: 'Java execution failed' });
    }

    console.log('Java Output:', stdout);

    // ✅ Đọc file route.json và trả kết quả về HTML luôn
    try {
      const routeData = fs.readFileSync('route.json', 'utf-8');
      const route = JSON.parse(routeData);
      res.send({ message: 'Route generated', route });
    } catch (err) {
      console.error('Error reading route.json:', err);
      res.status(500).send({ error: 'Failed to read route.json' });
    }
  });
});

app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});
