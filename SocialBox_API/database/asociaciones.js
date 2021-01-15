const Usuario = require('./models/Usuario');
const Picture = require('./models/Picture');
const Friendship = require('./models/Friendship');
const Reaction = require('./models/Reaction');
const Comment = require('./models/Comment')

Usuario.hasMany(Picture, {onDelete: 'CASCADE', onUpdate: 'CASCADE'});
Usuario.belongsToMany(Usuario, { as: 'owner', 
    through: Friendship, 
    foreignKey:'senderId',
    onDelete: 'CASCADE',
    onUpdate: 'CASCADE' });
Usuario.belongsToMany(Usuario, { as: 'friend', through: Friendship, foreignKey:'receptorId' });
Picture.belongsTo(Usuario, {onDelete: 'CASCADE', onUpdate: 'CASCADE'});
Picture.hasMany(Reaction, {onDelete: 'CASCADE', onUpdate: 'CASCADE'});
Reaction.belongsTo(Picture, {onDelete: 'CASCADE', onUpdate: 'CASCADE'});
Picture.hasMany(Comment, {onDelete:'CASCADE', onUpdate: 'CASCADE'});
Comment.belongsTo(Picture, {onDelete: 'CASCADE', onUpdate: 'CASCADE'});