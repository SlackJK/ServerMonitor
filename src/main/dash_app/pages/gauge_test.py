import dash
from dash import html, dcc, callback, Input, Output
import plotly.express as px
import plotly.graph_objs as go
import plotly.io as pio

dash.register_page(__name__)

# TODO fix bs figure that doesnt work
# arc path https://community.plotly.com/t/arc-shape-with-path/7205/5
fig = {
    "data": [{"type": "pie",
              "values": [50, 20, 30],
              "hole":.75,
              "sort":False,
              "direction": "clockwise",
              "textinfo": ""
              }]
}
"""
Failed component prop type: Invalid component prop `figure` key `values` supplied to Graph.
Bad object: {
  "values": [
    50,
    30,
    20
  ],
  "marker": {
    "line": {
      "width": 2,
      "color": "black"
    }
  },
  "hole": 0.75,
  "type": "pie",
  "direction": "clockwise",
  "sort": false,
  "showlegend": false
}
Valid keys: [
  "data",
  "layout",
  "frames"
]

"""

layout = html.Div([
    html.H1("this is a temp test page"),
    html.P("custom gauge test"),
    html.Div(
        dcc.Graph(figure=fig)
    )
])
